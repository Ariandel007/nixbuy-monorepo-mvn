package com.mvnnixbuyapi.paymentservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.paymentservice.dto.request.CreateOrderDto;
import com.mvnnixbuyapi.paymentservice.models.Order;
import com.mvnnixbuyapi.paymentservice.models.OrderStates;
import com.mvnnixbuyapi.paymentservice.models.OutboxTable;
import com.mvnnixbuyapi.paymentservice.repositories.OrderRepository;
import com.mvnnixbuyapi.paymentservice.repositories.OutboxTableRepository;
import com.mvnnixbuyapi.paymentservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OutboxTableRepository outboxTableRepository;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            OutboxTableRepository outboxTableRepository
    ) {
        this.orderRepository = orderRepository;
        this.outboxTableRepository = outboxTableRepository;
    }

    @Override
    public boolean checkIfOrderIsForUser(Long orderId, Long userId) {
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public Order createOrder(CreateOrderDto createOrderDto) {
        // CALL ALLOWED PRODUCTS
        Order orderToSave = Order.builder()
                .totalPriceWithoutTaxes(new BigDecimal("30.88"))
                .taxesPercentage(new BigDecimal("1.0"))
                .currencyCode("USD")
                .creationDate(Instant.now())
                .expirationDate(Instant.now().plus(1, ChronoUnit.HOURS))
                .userId(createOrderDto.getUserId())
                .status(OrderStates.PENDING.toString())
                .build();
        Order orderCreated = this.orderRepository.save(orderToSave);

        byte[] dataBytes = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            dataBytes = objectMapper.writeValueAsBytes(orderCreated);
        } catch (Exception e) {
            throw new RuntimeException("ERROR PROVISIONAL");
        }

        OutboxTable outboxTableToInsert = OutboxTable.builder()
                .eventType("OrderCreated")
                .timestamp(Instant.now())
                .data(dataBytes)
                .aggregateId(orderCreated.getId().toString())
                .aggregateType("OrderTable")
                .build();

        this.outboxTableRepository.save(outboxTableToInsert);
        return orderToSave;
    }
}
