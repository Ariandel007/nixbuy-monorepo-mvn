package com.mvnnixbuyapi.keyProduct.port.repository;

import com.mvnnixbuyapi.keyProduct.model.entity.KeyProduct;

public interface KeyRepository {
    KeyProduct create(KeyProduct product);
    KeyProduct edit(KeyProduct product);
    KeyProduct find(KeyProduct product);
}
