package com.mvnnixbuyapi.paymentservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.paymentservice.clients.feign.ProductsFeign;
import com.mvnnixbuyapi.paymentservice.dto.request.CreateOrderDto;
import com.mvnnixbuyapi.paymentservice.dto.request.ItemCartDto;
import com.mvnnixbuyapi.paymentservice.dto.sendToKafka.OrderKafkaDto;
import com.mvnnixbuyapi.paymentservice.dto.sendToKafka.OrderStatusUpdateKafkaDto;
import com.mvnnixbuyapi.paymentservice.dto.sendToKafka.ProductKafkaDto;
import com.mvnnixbuyapi.paymentservice.mapper.OrderMapper;
import com.mvnnixbuyapi.paymentservice.models.Order;
import com.mvnnixbuyapi.commons.enums.OrderStates;
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
                .status(OrderStates.PENDING.name())
                .build();
        // SAVE
        ResultMonad<Order> orderCreated = ResultMonad.ok(this.orderRepository.save(orderToSave));

        // SEND EVENT TO DEBEZIUM
        //TODO: ADD PRODUCTS LIST TO MESSAGE
        // IMPORTANT IF WE WANT TO CONTINUE!
        // 1. Send message order with products list.
        // we should also have a model in this BD just for products ids
        // 2. THAT DTO is gonna be processed in OrderCommandKafkaListener.
        // 3. If that id doesn't exists, in base to that products, we are gonna fill
        // the relationship to KeyProduct. If this id exists, we are not gonna insert.
        // 4. For start stripe payments, we must check that order was created in products inventary.
        // we should add and inner count for that. 40 requests mean that we should abort.
        // 5. If an order is booked for more than one day. That's gonna rollback.
        // 6. When StripeWebhookController accepts our request, we are gonna send a kafka message indication
        // that is was processed.
        // 7. Check events for cancelling payments.

        Order orderCreatedValue = orderCreated.getValue();
        OrderKafkaDto orderKafkaDto = OrderMapper.INSTANCE.toDtoCreate(orderCreatedValue);
        List<ProductKafkaDto> productKafkaDtosToSend = responseForBodyResponseEntityProducts.stream()
                                                       .map(p->new ProductKafkaDto(p.getId(), p.getQuantity()))
                                                       .toList();
        orderKafkaDto.setProductList(productKafkaDtosToSend);

        byte[] dataBytes = null;

        try {
            ObjectMapper objectMapper = JsonMapper.builder()
                    .findAndAddModules()
                    .build();
            dataBytes = objectMapper.writeValueAsBytes(orderKafkaDto);
        } catch (Exception e) {
            //  TODO: CHANGE EXCEPTION
            throw new RuntimeException("ERROR PROVISIONAL");
        }

        OutboxTable outboxTableToInsert = OutboxTable.builder()
                .eventType("OrderCreated")
                .timestamp(Instant.now())
                .data(dataBytes)
                .aggregateId(orderKafkaDto.getId().toString())
                .aggregateType("OrderTable")
                .build();

        this.outboxTableRepository.save(outboxTableToInsert);
        return orderCreated;
    }

    @Override
    @Transactional(readOnly = false)
    public ResultMonad<Order> updateOrderStatusById(Long orderId, String orderStatus) {
        var optionalOrderInBD = this.orderRepository.findById(orderId);
        if (optionalOrderInBD.isEmpty()){
            //TODO: HANDLE ORDER NOT PRESENT
            return ResultMonad.error("ERROR_ORDER_ID_NOT_FOUND");
        }
        var orderToUpdate = optionalOrderInBD.get();
        orderToUpdate.setStatus(orderStatus);
        Order orderUpdated = this.orderRepository.save(orderToUpdate);
        OrderStatusUpdateKafkaDto orderStatusUpdateKafkaDto = OrderMapper.INSTANCE.toDtoStatusOrder(orderUpdated);
        //
        byte[] dataBytes = null;

        try {
            ObjectMapper objectMapper = JsonMapper.builder()
                    .findAndAddModules()
                    .build();
            dataBytes = objectMapper.writeValueAsBytes(orderStatusUpdateKafkaDto);
        } catch (Exception e) {
            //  TODO: CHANGE EXCEPTION
            throw new RuntimeException("ERROR PROVISIONAL");
        }
        //TODO: ANALYZE MORE EVENTS
        if(orderStatus.equals(OrderStates.EXECUTED.name()) || orderStatus.equals(OrderStates.CANCELED.name())) {
            OutboxTable outboxTableToInsert = OutboxTable.builder()
                    .eventType(OrderStates.EXECUTED.name().equals(orderStatus) ? "OrderStatusPaymentExecuted": "OrderStatusPaymentCanceled")
                    .timestamp(Instant.now())
                    .data(dataBytes)
                    .aggregateId(orderStatusUpdateKafkaDto.getId().toString())
                    .aggregateType("OrderTable")
                    .build();

            this.outboxTableRepository.save(outboxTableToInsert);
        }
        //
        return ResultMonad.ok(orderUpdated);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultMonad<Order> findOrderById(Long orderId) {
        var optionalOrder = this.orderRepository.findById(orderId);
        //TODO: CREATE CUSTOM ERROR
        return optionalOrder
                .map(order -> ResultMonad.ok(order))
                .orElseGet(() -> ResultMonad.error("ERROR"));
    }
}
