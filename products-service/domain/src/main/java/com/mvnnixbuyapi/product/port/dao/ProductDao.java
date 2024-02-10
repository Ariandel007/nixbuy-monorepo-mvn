package com.mvnnixbuyapi.product.port.dao;

import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.query.ItemCartDto;

import java.util.List;

public interface ProductDao {
    List<ProductDto> getProductByIds(Long orderId);
    List<ProductDto> getProductsAvailable(List<ItemCartDto> itemCartDtoList);

}
