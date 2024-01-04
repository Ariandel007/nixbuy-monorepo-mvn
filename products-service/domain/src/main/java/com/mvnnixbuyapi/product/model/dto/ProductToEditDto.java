package com.mvnnixbuyapi.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductToEditDto {
    private Long id;
    private String name;
    private String description;
    private String urlImage;
    private BigDecimal price;
}
