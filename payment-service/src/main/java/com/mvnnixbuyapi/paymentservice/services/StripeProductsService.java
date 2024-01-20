package com.mvnnixbuyapi.paymentservice.services;

import com.stripe.model.Product;

import java.util.List;

public interface StripeProductsService {
    List<Product> getProductsById(List<Long> ids);
}
