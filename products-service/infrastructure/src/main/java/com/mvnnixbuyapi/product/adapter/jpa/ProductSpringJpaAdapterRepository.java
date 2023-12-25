package com.mvnnixbuyapi.product.adapter.jpa;

import com.mvnnixbuyapi.product.adapter.entity.ProductEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductSpringJpaAdapterRepository extends JpaRepository<ProductEntity, Long> {
}
