package com.mvnnixbuyapi.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductToEditDto {
    private Long id;
    private String name;
    private String description;
    private String urlImage;
}
