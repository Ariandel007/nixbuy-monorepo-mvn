package com.mvnnixbuyapi.keyProduct.port.repository;

import com.mvnnixbuyapi.keyProduct.model.entity.KeyProduct;
import com.mvnnixbuyapi.product.model.dto.ProductReceivedDto;
import com.mvnnixbuyapi.product.model.entity.Product;

import java.util.List;

public interface KeyRepository {
    KeyProduct create(KeyProduct product);
    KeyProduct edit(KeyProduct product);
    KeyProduct find(KeyProduct product);

    List<KeyProduct> findByProductsId(List<ProductReceivedDto> productList);

}
