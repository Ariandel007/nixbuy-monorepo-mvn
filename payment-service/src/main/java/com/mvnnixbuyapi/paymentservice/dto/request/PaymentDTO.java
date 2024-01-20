package com.mvnnixbuyapi.paymentservice.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PaymentDTO {
    private List<Long> itemIdList;
    private String customerName;
    private String customerEmail;
}