package com.mvnnixbuyapi.paymentservice.dto.request;

import lombok.Data;

@Data
public class PaymentDto {
    private Long idPlatform;
    private String customerName;
    private String customerEmail;
    private Long orderId;
}
