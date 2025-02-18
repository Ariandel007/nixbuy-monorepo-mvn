package com.mvnnixbuyapi.paymentservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.commons.enums.OrderStates;
import com.mvnnixbuyapi.paymentservice.services.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class StripeWebhookController {

    private final ObjectMapper objectMapper;

    private final String STRIPE_WEBHOOK_SECRET;
    private final OrderService orderService;

//    stripe listen --forward-to host.docker.internal:3804/stripe/webhook --skip-verify
    @Autowired
    public StripeWebhookController(
            ObjectMapper objectMapper,
            @Value("${STRIPE_WEBHOOK_SECRET}") String STRIPE_WEBHOOK_SECRET,
            OrderService orderService
    ) {
        this.objectMapper = objectMapper;
        this.STRIPE_WEBHOOK_SECRET = STRIPE_WEBHOOK_SECRET;
        this.orderService = orderService;
    }

    @PostMapping("/stripe/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        // Verificar la firma de la solicitud webhook
        try {
            Event event = Webhook.constructEvent(
                    payload, sigHeader, STRIPE_WEBHOOK_SECRET
            );

            // Obtener el tipo de evento
            String eventType = event.getType();

            // Manejar el evento según su tipo
            if ("payment_intent.succeeded".equals(eventType)) {
                // Manejar el pago exitoso aquí
                // Puedes obtener información adicional del evento con event.getDataObject()

                // Por ejemplo, loguear el ID del pago exitoso
                log.info("Pago exitoso. ID del pago: " + event.getId());
                EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
                if (!dataObjectDeserializer.getRawJson().isEmpty()) {
                    Map<String, Object> paymentIntentMap = objectMapper.readValue(dataObjectDeserializer.getRawJson(), Map.class);
                    // Access to metada
                    Map<String, String> metadata = (Map<String, String>) paymentIntentMap.get("metadata");
                    String orderId = metadata.get("orderId");
                    String idUser = metadata.get("idUser");

                    // Convert to Long
                    Long numberOrderId = Long.valueOf(orderId);
                    Long numberIdUser = Long.valueOf(idUser);
                    this.orderService.updateOrderStatusById(numberOrderId, OrderStates.EXECUTED.name());

                    log.info("Pago exitoso. ID del paymentIntent: " + paymentIntentMap.get("id"));
                    log.info("orderId: " + orderId + ", idUser: " + idUser);

                } else {
                    // El objeto no se pudo deserializar
                    log.warn("No se pudo deserializar el objeto PaymentIntent.");
                }
            }

            if ("payment_intent.payment_failed".equals(eventType)) {
                // Notificar que el pago fallo
                log.info("Pago fallido. ID del pago: " + event.getId());
            }

            // Otros casos de manejo de eventos aquí...

            return new ResponseEntity<>("Stripe event successfully processed", HttpStatus.OK);
        } catch (SignatureVerificationException e) {
            // If the request signature does not match your webhook secret
            return new ResponseEntity<>("Firma inválida", HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            // Fail JSON
            throw new RuntimeException(e);
        }
    }


}
