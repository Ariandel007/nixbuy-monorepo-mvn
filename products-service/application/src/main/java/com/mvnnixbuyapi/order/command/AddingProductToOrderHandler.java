package com.mvnnixbuyapi.order.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;
import com.mvnnixbuyapi.order.service.CreatePendingOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Component
@Slf4j
public class AddingProductToOrderHandler {

    private final ObjectMapper mapper;
    private final CreatePendingOrderService createPendingOrderService;

    @Autowired
    public AddingProductToOrderHandler(
            @Qualifier("generalObjectMapper") ObjectMapper mapper,
            CreatePendingOrderService createPendingOrderService
    ) {
        this.mapper = mapper;
        this.createPendingOrderService = createPendingOrderService;
    }

    @Transactional
    public void execute(String base64Json) {
        String json = "";
        try{
            byte[] decodedBytes = Base64.getDecoder().decode(base64Json);
            json = new String(decodedBytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace(); // Manejo de errores si ocurre algún problema al procesar el JSON
            return;
        }
        OrderReceivedDto orderDto = null;
        try {
            orderDto = mapper.readValue(json, OrderReceivedDto.class);
            createPendingOrderService.execute(orderDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return;
        }
    }
}
