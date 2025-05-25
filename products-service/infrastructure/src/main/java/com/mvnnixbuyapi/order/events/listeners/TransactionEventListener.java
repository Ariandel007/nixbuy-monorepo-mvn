package com.mvnnixbuyapi.order.events.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mvnnixbuyapi.commons.utils.JsonUtils;
import com.mvnnixbuyapi.order.events.OrderErrorEvent;
import com.mvnnixbuyapi.outbox.adapters.entity.OutboxTable;
import com.mvnnixbuyapi.outbox.adapters.jpa.OutboxTableSpringJpaAdapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TransactionEventListener {

    private final OutboxTableSpringJpaAdapterRepository outboxTableSpringJpaAdapterRepository;
    private final ObjectMapper mapper;

    @Autowired
    public TransactionEventListener(OutboxTableSpringJpaAdapterRepository outboxTableSpringJpaAdapterRepository, ObjectMapper mapper) {
        this.outboxTableSpringJpaAdapterRepository = outboxTableSpringJpaAdapterRepository;
        this.mapper = mapper;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleAfterRollback(OrderErrorEvent event) throws JsonProcessingException {
        if (event.getMessage().equals("ROLLBACK_OCCURRED_IN_ADDING_PRODUCT_TO_ORDER")) {
            byte[] dataBytes = JsonUtils.toJsonBytes(event.getOrder(), this.mapper);
            OutboxTable outboxTableToInsert = OutboxTable.builder()
                    .eventType("OrderError")
                    .timestamp(Instant.now())
                    .data(dataBytes)
                    .aggregateId(event.getOrder().getId().toString())
                    .aggregateType("OrderTable")
                    .build();
            this.outboxTableSpringJpaAdapterRepository.save(outboxTableToInsert);
        }
    }
}