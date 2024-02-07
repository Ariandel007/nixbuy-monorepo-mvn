package com.mvnnixbuyapi.paymentservice.repositories;

import com.mvnnixbuyapi.paymentservice.models.OutboxTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxTableRepository extends JpaRepository<OutboxTable, String> {
}
