package com.mvnnixbuyapi.keyProduct.adapters.jpa;

import com.mvnnixbuyapi.keyProduct.adapters.entity.KeyProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeySpringJpaAdapterRepository extends JpaRepository<KeyProductEntity, Long> {
}
