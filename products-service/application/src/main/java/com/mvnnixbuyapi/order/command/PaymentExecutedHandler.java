package com.mvnnixbuyapi.order.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;
import com.mvnnixbuyapi.order.service.UpdateStatusOfOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Component
@Slf4j
public class PaymentExecutedHandler {
    private final ObjectMapper mapper;
    private final UpdateStatusOfOrderService updateStatusOfOrderService;

    @Autowired
    public PaymentExecutedHandler(@Qualifier("generalObjectMapper") ObjectMapper mapper,
                                  UpdateStatusOfOrderService updateStatusOfOrderService) {
        this.mapper = mapper;
        this.updateStatusOfOrderService = updateStatusOfOrderService;
    }

    @Transactional
    public void execute(String base64Json) {
        // TODO: Consider to use @TransactionalEventListener and ApplicationEventPublisher for sending events in case of rollback for RunTimeException
        String json = "";
        try{
            byte[] decodedBytes = Base64.getDecoder().decode(base64Json);
            json = new String(decodedBytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace(); // TODO: Error handling if any problem occurs while processing the JSON
            return;
        }
        OrderReceivedDto orderDto = null;
        try {
            orderDto = mapper.readValue(json, OrderReceivedDto.class);
            updateStatusOfOrderService.execute(orderDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return;
        }
    }
}
