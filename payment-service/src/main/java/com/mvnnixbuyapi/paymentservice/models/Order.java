package com.mvnnixbuyapi.paymentservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = 1003100000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(name = "total_price_without_taxes")
    private BigDecimal totalPriceWithoutTaxes;
    @Column(name = "taxes_percentage")
    private BigDecimal taxesPercentage;
    @Column(name = "description", length = 255)
    private String description;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "expiration_date")
    private Instant expirationDate;
    @Column(name = "creation_date")
    private Instant creationDate;

}
