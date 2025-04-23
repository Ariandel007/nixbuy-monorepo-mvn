package com.mvnnixbuyapi.order.kafka.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mvnnixbuyapi.order.command.AddingProductToOrderHandler;
import com.mvnnixbuyapi.order.command.PaymentExecutedHandler;
import com.mvnnixbuyapi.product.model.dto.command.OutboxTableDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.KafkaHeaders;

import java.util.HashMap;

@Component
@Slf4j
public class OrderCommandKafkaListener {

    private final AddingProductToOrderHandler addingProductToOrderHandler;
    private final PaymentExecutedHandler paymentExecutedHandler;
    private final ObjectMapper mapper;

    @Autowired
    public OrderCommandKafkaListener(
            AddingProductToOrderHandler addingProductToOrderHandler,
            PaymentExecutedHandler paymentExecutedHandler,
            @Qualifier("generalObjectMapper") ObjectMapper mapper
    ) {
        this.addingProductToOrderHandler = addingProductToOrderHandler;
        this.paymentExecutedHandler = paymentExecutedHandler;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "pg-topic.public.outbox_table", groupId = "consume-payment-outbox-1")
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            Acknowledgment acknowledgment) throws JsonProcessingException {
        log.info("Received Message: " + message + " from partition: " + partition);
        mapper.registerModule(new JavaTimeModule());
        // Listener
        OutboxTableDto outboxTableAfter = null;
        // Parse JSON to HashMap
        HashMap<String, Object> map = mapper.readValue(message, new TypeReference<HashMap<String,Object>>() {});
        HashMap<String, Object> payloadMessage =  (HashMap<String, Object>) map.get("payload");
        outboxTableAfter = mapper.convertValue(payloadMessage.get("after"), OutboxTableDto.class);

        //TODO: CREATE UPDATE DATE COLUMN IN ORDER TABLE SO WE CAN COMPARE EVENTS DATES AND IGNORING THE OLDER TIMESTAMPS
        try {
            switch (outboxTableAfter.getAggregateType() + "-" + outboxTableAfter.getEventType()) {
                case "OrderTable-OrderCreated":
                    // secuencia
                    this.addingProductToOrderHandler.execute(outboxTableAfter.getData());
                    break;
                case "OrderTable-OrderStatusPaymentExecuted":
                    this.paymentExecutedHandler.execute(outboxTableAfter.getData());
                    break;
                case "OrderTable-OrderStatusPaymentCanceled":
                    //TODO: Command to cancel order
                    break;
            }

            acknowledgment.acknowledge(); // Acknowledge the message after processing
        } catch (Exception e) {
            acknowledgment.acknowledge();
            throw e; // Ensure transaction rollback
        }
    }
}
