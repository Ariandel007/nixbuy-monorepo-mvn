package com.mvnnixbuyapi.product.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.order.model.dto.OrderDto;
import com.mvnnixbuyapi.product.model.dto.command.OutboxTableDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.HashMap;

@Component
@Slf4j
public class AddingProductToOrderHandler {

    private final ObjectMapper mapper;

    @Autowired
    public AddingProductToOrderHandler(@Qualifier("generalObjectMapper") ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    public void execute(String message) {
        String base64Json = "";
        try {
            // Convertir JSON a HashMap
            HashMap<String, Object> map = mapper.readValue(message, new TypeReference<HashMap<String,Object>>() {});
            HashMap<String, Object> payloadMessage =  (HashMap<String, Object>) map.get("payload");
            OutboxTableDto outboxTableAfter = mapper.convertValue(payloadMessage.get("after"), OutboxTableDto.class);
            base64Json = outboxTableAfter.getData();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace(); // Manejo de errores si ocurre algún problema al procesar el JSON
            return;
        }
        String json = "";
        try{
            byte[] decodedBytes = Base64.getDecoder().decode(base64Json);
            json = new String(decodedBytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace(); // Manejo de errores si ocurre algún problema al procesar el JSON
            return;
        }

        try {
            OrderDto orderDto = mapper.readValue(json, OrderDto.class);

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return;
        }
    }
}
