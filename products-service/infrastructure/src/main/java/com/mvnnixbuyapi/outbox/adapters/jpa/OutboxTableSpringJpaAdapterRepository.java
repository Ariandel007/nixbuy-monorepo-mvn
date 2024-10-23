package com.mvnnixbuyapi.outbox.adapters.jpa;

import com.mvnnixbuyapi.outbox.adapters.entity.OutboxTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxTableSpringJpaAdapterRepository extends JpaRepository<OutboxTable, Long> {

}
