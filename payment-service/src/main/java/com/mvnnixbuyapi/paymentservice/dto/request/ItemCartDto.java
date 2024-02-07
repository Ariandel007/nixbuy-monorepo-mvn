package com.mvnnixbuyapi.paymentservice.dto.request;

import lombok.Data;

@Data
public class ItemCartDto {
    private Long productId;
    private int quantity;
}
