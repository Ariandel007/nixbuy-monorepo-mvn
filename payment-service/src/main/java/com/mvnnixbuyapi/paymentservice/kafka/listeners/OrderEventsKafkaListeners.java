package com.mvnnixbuyapi.paymentservice.kafka.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.commons.enums.OrderStates;
import com.mvnnixbuyapi.commons.utils.JsonUtils;
import com.mvnnixbuyapi.paymentservice.dto.receivedFromKafka.OutboxTableDto;
import com.mvnnixbuyapi.paymentservice.models.Order;
import com.mvnnixbuyapi.paymentservice.services.OrderService;
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
    private final OrderService orderService;

    @Autowired
    public OrderEventsKafkaListeners(
            ObjectMapper mapper,
            OrderService orderService
    ) {
        this.mapper = mapper;
        this.orderService = orderService;
    }

    @KafkaListener(topics = "pg-topic.public.outbox_table", groupId = "consume-payment-outbox-1")
    public void listenWithHeaders(@Payload String message,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) throws JsonProcessingException {
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
                this.eventOfOrderSendedByKafka(outboxTableAfter.getData(), OrderStates.PENDING_CONFIRMED.name());
                break;
            case "OrderTable-OrderExecutedConfirmed":
                this.eventOfOrderSendedByKafka(outboxTableAfter.getData(), OrderStates.EXECUTED_CONFIRMED.name());
                break;
        }

    }

    private void eventOfOrderSendedByKafka(String jsonBase64,  String orderStatus) throws JsonProcessingException {
        String json = JsonUtils.convertBase64JsonToJson(jsonBase64);
        Order order = mapper.readValue(json, Order.class);
        this.orderService.updateOrderStatusById(order.getId(), orderStatus);
    }
}
