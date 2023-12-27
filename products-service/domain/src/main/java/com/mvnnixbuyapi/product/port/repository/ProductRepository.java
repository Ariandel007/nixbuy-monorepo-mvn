package com.mvnnixbuyapi.product.port.repository;

import com.mvnnixbuyapi.product.model.entity.Product;

public interface ProductRepository {
    Product create(Product product);
    Product edit(Product product);

}
