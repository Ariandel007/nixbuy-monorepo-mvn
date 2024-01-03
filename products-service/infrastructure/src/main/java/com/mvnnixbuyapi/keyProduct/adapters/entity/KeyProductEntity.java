package com.mvnnixbuyapi.keyProduct.adapters.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "key_products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KeyProductEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_id")
    private Long id;
    @Column(name = "key_code", unique=true)
    private String KeyCode;
    private BigDecimal price;
    @Column(name = "status", length = 10)
    private String status;
    @Column(name = "create_date")
    private Instant createDate;
    @Column(name = "active_date")
    private Instant activeDate;
    @Column(name = "inactive_date")
    private Instant inactiveDate;
    @Column(name = "sold_date")
    private Instant soldDate;

}
