package com.mvnnixbuyapi.order.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.commons.utils.JsonUtils;
import com.mvnnixbuyapi.order.events.OrderErrorEvent;
import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;
import com.mvnnixbuyapi.order.service.UpdateStatusOfOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Base64;

@Component
@Slf4j
public class PaymentExecutedHandler {
    private final ObjectMapper mapper;
    private final UpdateStatusOfOrderService updateStatusOfOrderService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public PaymentExecutedHandler(@Qualifier("generalObjectMapper") ObjectMapper mapper,
                                  UpdateStatusOfOrderService updateStatusOfOrderService,
                                  ApplicationEventPublisher publisher) {
        this.mapper = mapper;
        this.updateStatusOfOrderService = updateStatusOfOrderService;
        this.publisher = publisher;
    }

    @Transactional
    public void execute(String base64Json) throws JsonProcessingException {
        String json = JsonUtils.convertBase64JsonToJson(base64Json);
        OrderReceivedDto orderDto = JsonUtils.fromJson(json, OrderReceivedDto.class, mapper);


        // Send event in case of rollback
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
                    publisher.publishEvent(
                            new OrderErrorEvent(
                                    "ROLLBACK_PAYMENT_EVENT_IN_ORDER",
                                    orderDto)
                    );
                }
            }
        });

        updateStatusOfOrderService.execute(orderDto);
    }
}
