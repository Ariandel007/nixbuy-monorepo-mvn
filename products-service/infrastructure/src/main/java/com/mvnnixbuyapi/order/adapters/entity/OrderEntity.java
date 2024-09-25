package com.mvnnixbuyapi.order.adapters.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    private Long id;
    @Column(name = "total_price_without_taxes")
    private BigDecimal totalPriceWithoutTaxes;
    @Column(name = "taxes_percentage")
    private BigDecimal taxesPercentage;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "expiration_date")
    private Instant expirationDate;
    @Column(name = "creation_date")
    private Instant creationDate;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "status") //PENDING,CONFIRMED,EXECUTED,CANCELED
    private String status;
}
