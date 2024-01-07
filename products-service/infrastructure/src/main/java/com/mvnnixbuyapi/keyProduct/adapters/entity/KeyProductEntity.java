package com.mvnnixbuyapi.keyProduct.adapters.entity;

import com.mvnnixbuyapi.platforms.adapters.entity.PlatformEntity;
import com.mvnnixbuyapi.product.adapter.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "key_products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KeyProductEntity implements Serializable {
    private static final long serialVersionUID = 1003100000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_id")
    private Long id;
    @Column(name = "key_code", unique=true)
    private String keyCode;
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

    // FKS:
    @Column(name = "plattform_id", nullable = false)
    private Long plattformId;
    @Column(name = "product_id", nullable = false)
    private Long productId;

    // Relations:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plattform_id", insertable=false, updatable=false)//insertable=false, updatable=false porque idUserApp esta siendo usado como el FK
    private PlatformEntity platformEntityRelated;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable=false, updatable=false)//insertable=false, updatable=false porque idUserApp esta siendo usado como el FK
    private ProductEntity productEntityRelated;

}
