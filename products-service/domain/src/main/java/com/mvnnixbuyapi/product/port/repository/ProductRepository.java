package com.mvnnixbuyapi.product.port.repository;

import com.mvnnixbuyapi.product.model.entity.Product;

import java.util.List;

public interface ProductRepository {
    Product create(Product product);
    Product edit(Product product);
    Product find(Product product);
    List<Product> findProductsById(List<Long> productIdList);
}
