package com.mvnnixbuyapi.paymentservice.dto.sendToKafka;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class OrderKafkaDto {
    private Long id;
    private BigDecimal totalPriceWithoutTaxes;
    private BigDecimal taxesPercentage;
    private String currencyCode;
    private Instant expirationDate;
    private Instant creationDate;
    private Long userId;
    private String status;
    private List<ProductKafkaDto> productList;
}
