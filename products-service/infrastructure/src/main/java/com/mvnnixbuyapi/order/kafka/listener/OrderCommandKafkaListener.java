package com.mvnnixbuyapi.order.kafka.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.order.command.AddingProductToOrderHandler;
import com.mvnnixbuyapi.product.model.dto.command.OutboxTableDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.KafkaHeaders;

import java.util.HashMap;

@Component
@Slf4j
public class OrderCommandKafkaListener {

    private final AddingProductToOrderHandler addingProductToOrderHandler;
    private final ObjectMapper mapper;

    @Autowired
    public OrderCommandKafkaListener(
            AddingProductToOrderHandler addingProductToOrderHandler,
            @Qualifier("generalObjectMapper") ObjectMapper mapper
    ) {
        this.addingProductToOrderHandler = addingProductToOrderHandler;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "pg-topic.public.outbox_table", groupId = "consume-payment-outbox-1")
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received Message: " + message + " from partition: " + partition);

        // Listener
        OutboxTableDto outboxTableAfter = null;
        try {
            // Convertir JSON a HashMap
            HashMap<String, Object> map = mapper.readValue(message, new TypeReference<HashMap<String,Object>>() {});
            HashMap<String, Object> payloadMessage =  (HashMap<String, Object>) map.get("payload");
            outboxTableAfter = mapper.convertValue(payloadMessage.get("after"), OutboxTableDto.class);
//            base64Json = outboxTableAfter.getData();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace(); // Manejo de errores si ocurre algún problema al procesar el JSON
            return;
        }

        switch (outboxTableAfter.getAggregateType()+"-"+outboxTableAfter.getEventType()) {
            case "OrderTable-OrderCreated":
                // secuencia
                this.addingProductToOrderHandler.execute(message);
                break;
        }

    }

}
