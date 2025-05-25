package com.mvnnixbuyapi.order.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.commons.utils.JsonUtils;
import com.mvnnixbuyapi.order.events.OrderErrorEvent;
import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;
import com.mvnnixbuyapi.order.service.CreatePendingOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Slf4j
public class AddingProductToOrderHandler {

    private final ObjectMapper mapper;
    private final CreatePendingOrderService createPendingOrderService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public AddingProductToOrderHandler(
            @Qualifier("generalObjectMapper") ObjectMapper mapper,
            CreatePendingOrderService createPendingOrderService,
            ApplicationEventPublisher publisher
    ) {
        this.mapper = mapper;
        this.createPendingOrderService = createPendingOrderService;
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
                                    "ROLLBACK_OCCURRED_IN_ADDING_PRODUCT_TO_ORDER",
                                    orderDto)
                    );
                }
            }
        });
        createPendingOrderService.execute(orderDto);
    }
}
