package com.mvnnixbuyapi.paymentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCartDto {
    private Long productId;
    private int quantity;
}
