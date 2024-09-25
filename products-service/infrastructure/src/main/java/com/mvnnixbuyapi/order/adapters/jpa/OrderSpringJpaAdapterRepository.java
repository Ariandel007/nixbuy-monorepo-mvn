package com.mvnnixbuyapi.order.adapters.jpa;

import com.mvnnixbuyapi.order.adapters.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSpringJpaAdapterRepository extends JpaRepository<OrderEntity, Long> {
}
