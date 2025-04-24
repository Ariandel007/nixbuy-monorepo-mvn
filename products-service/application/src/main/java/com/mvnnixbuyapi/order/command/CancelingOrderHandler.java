package com.mvnnixbuyapi.order.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.commons.utils.JsonUtils;
import com.mvnnixbuyapi.order.events.OrderErrorEvent;
import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;
import com.mvnnixbuyapi.order.service.CancelingStatusOfOrderService;
import com.mvnnixbuyapi.order.service.UpdateStatusOfOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Slf4j
public class CancelingOrderHandler {
    private final CancelingStatusOfOrderService cancelingStatusOfOrderService;
    private final ObjectMapper mapper;

    public CancelingOrderHandler(
            CancelingStatusOfOrderService cancelingStatusOfOrderService,
            @Qualifier("generalObjectMapper") ObjectMapper mapper) {
        this.cancelingStatusOfOrderService = cancelingStatusOfOrderService;
        this.mapper = mapper;
    }

    @Transactional
    public void execute(String base64Json) throws JsonProcessingException {
        String json = JsonUtils.convertBase64JsonToJson(base64Json);
        OrderReceivedDto orderDto = JsonUtils.fromJson(json, OrderReceivedDto.class, mapper);
        cancelingStatusOfOrderService.execute(orderDto);
    }
}
