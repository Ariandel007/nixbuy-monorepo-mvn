package com.mvnnixbuyapi.order.adapters.jpa.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mvnnixbuyapi.order.adapters.jpa.OrderSpringJpaAdapterRepository;
import com.mvnnixbuyapi.order.adapters.mapper.OrderDboMapper;
import com.mvnnixbuyapi.order.model.entity.Order;
import com.mvnnixbuyapi.order.port.repository.OrderRepository;
import com.mvnnixbuyapi.outbox.adapters.entity.OutboxTable;
import com.mvnnixbuyapi.outbox.adapters.jpa.OutboxTableSpringJpaAdapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Repository
@Transactional(readOnly = false)
public class OrderPostgresRepository implements OrderRepository {
    private final OrderSpringJpaAdapterRepository orderSpringJpaAdapterRepository;
    private final OrderDboMapper orderDboMapper;
    private final OutboxTableSpringJpaAdapterRepository outboxTableSpringJpaAdapterRepository;

    @Autowired
    public OrderPostgresRepository(
            OrderSpringJpaAdapterRepository orderSpringJpaAdapterRepository,
            OrderDboMapper orderDboMapper,
            OutboxTableSpringJpaAdapterRepository outboxTableSpringJpaAdapterRepository
    ) {
        this.orderSpringJpaAdapterRepository = orderSpringJpaAdapterRepository;
        this.orderDboMapper = orderDboMapper;
        this.outboxTableSpringJpaAdapterRepository = outboxTableSpringJpaAdapterRepository;
    }

    @Override
    public Order create(Order product) {
        var orderEntityToCreate = this.orderDboMapper.domainToEntity(product);
        var orderEntityCreated = this.orderSpringJpaAdapterRepository.save(orderEntityToCreate);
        //
        //TODO: MAKE DTO OF ORDER
        byte[] dataBytes = null;

        try {
            ObjectMapper objectMapper = JsonMapper.builder()
                    .findAndAddModules()
                    .build();
            dataBytes = objectMapper.writeValueAsBytes(orderEntityCreated);
        } catch (Exception e) {
            //  TODO: CHANGE EXCEPTION
            throw new RuntimeException("ERROR PROVISIONAL");
        }

        OutboxTable outboxTableToInsert = OutboxTable.builder()
                .eventType("OrderPendingConfirmed")
                .timestamp(Instant.now())
                .data(dataBytes)
                .aggregateId(orderEntityCreated.getId().toString())
                .aggregateType("OrderTable")
                .build();
        this.outboxTableSpringJpaAdapterRepository.save(outboxTableToInsert);
        //
        return this.orderDboMapper.entityToDomain(orderEntityCreated);
    }

    @Override
    public Order updateOrder(Order product) {
        var orderEntityToCreate = this.orderDboMapper.domainToEntity(product);
        var orderEntityCreated = this.orderSpringJpaAdapterRepository.save(orderEntityToCreate);
        //
        //TODO: MAKE DTO OF ORDER
        byte[] dataBytes = null;

        try {
            ObjectMapper objectMapper = JsonMapper.builder()
                    .findAndAddModules()
                    .build();
            dataBytes = objectMapper.writeValueAsBytes(orderEntityCreated);
        } catch (Exception e) {
            //  TODO: CHANGE EXCEPTION
            throw new RuntimeException("ERROR PROVISIONAL");
        }

        OutboxTable outboxTableToInsert = OutboxTable.builder()
                .eventType("OrderExecutedConfirmed")
                .timestamp(Instant.now())
                .data(dataBytes)
                .aggregateId(orderEntityCreated.getId().toString())
                .aggregateType("OrderTable")
                .build();
        this.outboxTableSpringJpaAdapterRepository.save(outboxTableToInsert);
        //
        return this.orderDboMapper.entityToDomain(orderEntityCreated);
    }
}
