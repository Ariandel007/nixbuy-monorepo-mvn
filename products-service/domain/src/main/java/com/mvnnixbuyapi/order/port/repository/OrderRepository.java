package com.mvnnixbuyapi.order.port.repository;

import com.mvnnixbuyapi.product.model.entity.Product;

public interface OrderRepository {
    Product create(Product product);

}
