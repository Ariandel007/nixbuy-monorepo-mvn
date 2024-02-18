package com.mvnnixbuyapi.order.kafka.listener;

import com.mvnnixbuyapi.product.command.AddingProductToOrderHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.KafkaHeaders;

@Component
@Slf4j
public class OrderCommandKafkaListener {

    private final AddingProductToOrderHandler addingProductToOrderHandler;

    @Autowired
    public OrderCommandKafkaListener(AddingProductToOrderHandler addingProductToOrderHandler) {
        this.addingProductToOrderHandler = addingProductToOrderHandler;
    }

    @KafkaListener(topics = "pg-topic.public.outbox_table", groupId = "consume-payment-outbox-1")
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received Message: " + message + " from partition: " + partition);
        this.addingProductToOrderHandler.execute(message);
    }

}
