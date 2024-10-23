package com.mvnnixbuyapi.paymentservice.kafka.listeners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.paymentservice.dto.receivedFromKafka.OutboxTableDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class OrderEventsKafkaListeners {
    private final ObjectMapper mapper;

    @Autowired
    public OrderEventsKafkaListeners(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @KafkaListener(topics = "pg-topic.public.outbox_table", groupId = "consume-payment-outbox-1")
    public void listenWithHeaders(@Payload String message,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received Message: " + message + " from partition: " + partition);

        // Listener
        OutboxTableDto outboxTableAfter = null;
        try {
            // Parse JSON to HashMap
            HashMap<String, Object> map = mapper.readValue(message, new TypeReference<HashMap<String,Object>>() {});
            HashMap<String, Object> payloadMessage =  (HashMap<String, Object>) map.get("payload");
            outboxTableAfter = mapper.convertValue(payloadMessage.get("after"), OutboxTableDto.class);
//            base64Json = outboxTableAfter.getData();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace(); // TODO: Error handling if any problem occurs while processing the JSON
            return;
        }

        //TODO: CREATE UPDATE DATE COLUMN IN ORDER TABLE SO WE CAN COMPARE EVENTS DATES AND IGNORING THE OLDER TIMESTAMPS

        switch (outboxTableAfter.getAggregateType()+"-"+outboxTableAfter.getEventType()) {
            case "OrderTable-OrderPendingConfirmed":
                // TODO: UPDATE
                break;
            case "OrderTable-OrderExecutedConfirmed":
                // TODO: UPDATE
                break;
        }

    }
}
