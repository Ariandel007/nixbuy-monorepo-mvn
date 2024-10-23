package com.mvnnixbuyapi.outbox.adapters.jpa;

import com.mvnnixbuyapi.order.adapters.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxTableSpringJpaAdapterRepository extends JpaRepository<OrderEntity, Long> {
}
