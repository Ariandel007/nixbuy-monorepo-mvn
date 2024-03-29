package com.mvnnixbuyapi.paymentservice.utils;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;

import java.math.BigDecimal;

public class UtilStripeApp {
    public static Customer findCustomerByEmail(String email) throws StripeException {
        CustomerSearchParams params =
                CustomerSearchParams
                        .builder()
                        .setQuery("email:'" + email + "'")
                        .build();
        CustomerSearchResult result = Customer.search(params);

        return result.getData().size() > 0 ? result.getData().get(0) : null;
    }

    public static Customer findOrCreateCustomer(String email, String name) throws StripeException {
        CustomerSearchParams params =
                CustomerSearchParams
                        .builder()
                        .setQuery("email:'" + email + "'")
                        .build();

        CustomerSearchResult result = Customer.search(params);

        Customer customer;
        // If no existing customer was found, create a new record
        if (result.getData().size() == 0) {
            CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                    .setName(name)
                    .setEmail(email)
                    .build();

            customer = Customer.create(customerCreateParams);
        } else {
            customer = result.getData().get(0);
        }
        return customer;
    }

    public static Product createProduct(String name, Long id, String currency, BigDecimal price) {
        Product sampleProduct = new Product();
        Price samplePrice = new Price();
        sampleProduct.setName(name);
        sampleProduct.setId(id.toString());

        samplePrice.setCurrency(currency);
        //Multiplicamos por 100 porque stripe espera enteros que luego seran pasados a decimales en su API
        samplePrice.setUnitAmountDecimal(price.multiply(new BigDecimal(100)));

        sampleProduct.setDefaultPriceObject(samplePrice);

        return sampleProduct;
    }

}
