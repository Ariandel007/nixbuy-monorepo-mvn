package com.mvnnixbuyapi.keyProduct.model.dto;

import lombok.Data;

@Data
public class KeyToCreateDto {
    private Long id;
    private String keyCode;
    private String status;
    private Long productId;
    private Long platformId;


}
