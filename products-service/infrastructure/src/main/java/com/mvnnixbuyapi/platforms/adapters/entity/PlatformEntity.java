package com.mvnnixbuyapi.platforms.adapters.entity;

import com.mvnnixbuyapi.keyProduct.adapters.entity.KeyProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "platforms")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlatformEntity implements Serializable {
    private static final long serialVersionUID = 100320000L;

    @Id
    @Column(name = "key_id")
    private Long id;
    @Column(name = "name", length = 255)
    private String name;
    @OneToMany(mappedBy = "platformEntityRelated", fetch = FetchType.LAZY)
    private List<KeyProductEntity> keyProductEntityList;
}
