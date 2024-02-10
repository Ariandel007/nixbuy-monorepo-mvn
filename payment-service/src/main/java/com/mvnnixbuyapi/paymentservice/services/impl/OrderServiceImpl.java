package com.mvnnixbuyapi.paymentservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.paymentservice.clients.feign.ProductsFeign;
import com.mvnnixbuyapi.paymentservice.dto.request.CreateOrderDto;
import com.mvnnixbuyapi.paymentservice.dto.request.ItemCartDto;
import com.mvnnixbuyapi.paymentservice.models.Order;
import com.mvnnixbuyapi.paymentservice.models.OrderStates;
import com.mvnnixbuyapi.paymentservice.models.OutboxTable;
import com.mvnnixbuyapi.paymentservice.repositories.OrderRepository;
import com.mvnnixbuyapi.paymentservice.repositories.OutboxTableRepository;
import com.mvnnixbuyapi.paymentservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OutboxTableRepository outboxTableRepository;

    private final ProductsFeign productsFeign;


    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            OutboxTableRepository outboxTableRepository,
            ProductsFeign productsFeign
    ) {
        this.orderRepository = orderRepository;
        this.outboxTableRepository = outboxTableRepository;
        this.productsFeign = productsFeign;
    }

    @Override
    public boolean checkIfOrderIsForUser(Long orderId, Long userId) {
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public ResultMonad<Order> createOrder(CreateOrderDto createOrderDto) {
        List<ItemCartDto> itemCartDtoList = createOrderDto.getItemCartDtoList();
        var responseForBodyResponseEntityProducts
                    = this.productsFeign.listResponseEntityProductDtoToAddToOrder(itemCartDtoList).getBody().getData();
        if(responseForBodyResponseEntityProducts.size() == 0){
            return ResultMonad.error("ERROR_PRODUCTS_ASKED_NOT_AVAILABLE");
        }
        BigDecimal totalPrice =
                responseForBodyResponseEntityProducts
                .stream()
                        .map(productDto -> productDto.getPrice().multiply(BigDecimal.valueOf(productDto.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        // CALL ALLOWED PRODUCTS
        Order orderToSave = Order.builder()
                .totalPriceWithoutTaxes(totalPrice)
                .taxesPercentage(new BigDecimal("1.0"))
                .currencyCode("USD")
                .creationDate(Instant.now())
                .expirationDate(Instant.now().plus(1, ChronoUnit.HOURS))
                .userId(createOrderDto.getUserId())
                .status(OrderStates.PENDING.toString())
                .build();
        ResultMonad<Order> orderCreated = ResultMonad.ok(this.orderRepository.save(orderToSave));

        byte[] dataBytes = null;

        try {
            ObjectMapper objectMapper = JsonMapper.builder()
                    .findAndAddModules()
                    .build();
            dataBytes = objectMapper.writeValueAsBytes(orderCreated.getValue());
        } catch (Exception e) {
            throw new RuntimeException("ERROR PROVISIONAL");
        }

        OutboxTable outboxTableToInsert = OutboxTable.builder()
                .eventType("OrderCreated")
                .timestamp(Instant.now())
                .data(dataBytes)
                .aggregateId(orderCreated.getValue().getId().toString())
                .aggregateType("OrderTable")
                .build();

        this.outboxTableRepository.save(outboxTableToInsert);
        return orderCreated;
    }
}
