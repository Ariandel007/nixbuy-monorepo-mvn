package com.mvnnixbuyapi.paymentservice.controllers;

import com.mvnnixbuyapi.paymentservice.dto.request.PaymentDTO;
import com.mvnnixbuyapi.paymentservice.services.StripeProductsService;
import com.mvnnixbuyapi.paymentservice.utils.UtilStripeApp;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payment/stripe")
public class PaymentStripeController {

    private final String stripeApiKey;
    private final String clientUrl;
    private final StripeProductsService stripeProductsService;

    @Autowired
    public PaymentStripeController(
            @Value("${STRIPE_API_KEY}") String stripeApiKey,
            @Value("${CLIENT_URL}") String clientUrl,
            StripeProductsService stripeProductsService
    )  {
        this.stripeApiKey = stripeApiKey;
        this.clientUrl = clientUrl;
        this.stripeProductsService = stripeProductsService;
    }

    @PostMapping("/v1/checkout/hosted")
    String hostedCheckout(@RequestBody PaymentDTO requestDTO) throws StripeException {
        // Start by finding an existing customer record from Stripe or creating a new one if needed
        Customer customer = UtilStripeApp.findOrCreateCustomer(requestDTO.getCustomerEmail(), requestDTO.getCustomerName());

        // Next, create a checkout session by adding the details of the checkout
        SessionCreateParams.Builder paramsBuilder =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCustomer(customer.getId())
                        .setSuccessUrl(this.clientUrl + "/success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl(this.clientUrl + "/failure");

        List<Product> productList = this.stripeProductsService.getProductsById(requestDTO.getItemIdList());
        for (Product product : productList) {
            paramsBuilder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .putMetadata("app_id", product.getId())
                                                            .setName(product.getName())
                                                            .build()
                                            )
                                            .setCurrency(product.getDefaultPriceObject().getCurrency())
                                            .setUnitAmountDecimal(product.getDefaultPriceObject().getUnitAmountDecimal())
                                            .build())
                            .build());
        }

        // Esto seria para generar facturas
        // paramsBuilder.setInvoiceCreation(SessionCreateParams.InvoiceCreation.builder().setEnabled(true).build());

        Session session = Session.create(paramsBuilder.build());
        return session.getUrl();
    }

    @PostMapping("/v1/checkout/integrated")
    String integratedCheckout(@RequestBody PaymentDTO requestDTO) throws StripeException {
        // Start by finding existing customer or creating a new one if needed
        Customer customer = UtilStripeApp.findOrCreateCustomer(requestDTO.getCustomerEmail(), requestDTO.getCustomerName());

        List<Product> productList = this.stripeProductsService.getProductsById(requestDTO.getItemIdList(), requestDTO.getIdPlatform());
        BigDecimal totalCost = productList.stream().map(x->x.getDefaultPriceObject().getUnitAmountDecimal()).reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create a PaymentIntent and send it's client secret to the client
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(totalCost.longValueExact())
                        .setCurrency("usd")
                        .setCustomer(customer.getId())
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // Send the client secret from the payment intent to the client
        // We should use stripe-js on the client side
        return paymentIntent.getClientSecret();
    }

}
