package com.mvnnixbuyapi.product.model.dto;

import com.mvnnixbuyapi.product.model.entity.valueobjects.ProductDescription;
import com.mvnnixbuyapi.product.model.entity.valueobjects.ProductId;
import com.mvnnixbuyapi.product.model.entity.valueobjects.ProductName;
import lombok.Data;

@Data
public class ProductDto {
    private ProductId id;
    private ProductName name;
    private ProductDescription description;
}
