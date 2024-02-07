package com.mvnnixbuyapi.product.port.dao;

import com.mvnnixbuyapi.product.model.dto.ProductDto;

import java.util.List;

public interface ProductDao {
    List<ProductDto> getProductByIds(Long orderId);
}
