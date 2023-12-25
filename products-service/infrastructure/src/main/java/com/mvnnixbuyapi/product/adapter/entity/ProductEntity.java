package com.mvnnixbuyapi.product.adapter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 1003000000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 255, name = "name")
    private String name;
    @Column(length = 255, name = "description")
    private String description;

    @Column(length = 255, name = "url_image")
    private String urlImage;
    @Column(name = "creation_date")
    private Instant creationDate;
    @Column(name = "update_date")
    private Instant updateDate;


}
