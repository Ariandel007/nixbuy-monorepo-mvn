package com.mvnnixbuyapi.paymentservice.controllers;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StripeWebhookController {

    private final String STRIPE_WEBHOOK_SECRET;
//    stripe listen --forward-to localhost:3804/stripe/webhook
    public StripeWebhookController(@Value("${STRIPE_WEBHOOK_SECRET}") String STRIPE_WEBHOOK_SECRET) {
        this.STRIPE_WEBHOOK_SECRET = STRIPE_WEBHOOK_SECRET;
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
            }

            if ("payment_intent.payment_failed".equals(eventType)) {
                // Notificar que el pago fallo
                log.info("Pago fallido. ID del pago: " + event.getId());
            }

            // Otros casos de manejo de eventos aquí...

            return new ResponseEntity<>("Stripe event successfully processed", HttpStatus.OK);
        } catch (SignatureVerificationException e) {
            // La firma de la solicitud no coincide con tu secreto de webhook
            return new ResponseEntity<>("Firma inválida", HttpStatus.BAD_REQUEST);
        }
    }


}
