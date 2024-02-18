package com.mvnnixbuyapi.order.kafka.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.KafkaHeaders;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "pg-topic.public.outbox_table", groupId = "consume-payment-outbox-1")
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received Message: " + message + "from partition: " + partition);
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Convertir JSON a HashMap
            HashMap<String, Object> map = mapper.readValue(message, new TypeReference<HashMap<String,Object>>() {});
//            Map<String, Object> payloadMessage =  (Map<String, Object>) map.get("payload");
//            OutboxTable outboxTableAfter = mapper.convertValue(payloadMessage.get("after"), OutboxTable.class);


        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Manejo de errores si ocurre algún problema al procesar el JSON
        }

    }

}
