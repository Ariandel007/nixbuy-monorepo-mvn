package com.mvnnixbuyapi.order.model.dto;

import com.mvnnixbuyapi.product.model.dto.ProductReceivedDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class OrderReceivedDto {
    private Long id;
    private BigDecimal totalPriceWithoutTaxes;
    private BigDecimal taxesPercentage;
    private String currencyCode;
    private Instant expirationDate;
    private Instant creationDate;
    private Long userId;
    private String status;

    private List<ProductReceivedDto> productList;


}
