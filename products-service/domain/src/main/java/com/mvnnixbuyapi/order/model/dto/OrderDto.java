package com.mvnnixbuyapi.order.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class OrderDto {
    private Long id;
    private BigDecimal totalPriceWithoutTaxes;
    private BigDecimal taxesPercentage;
    private String currencyCode;
    private Instant expirationDate;
    private Instant creationDate;
    private Long userId;
    private String status;

}
