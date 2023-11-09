package com.mvnnixbuyapi.gatewayservice.userservice.repositories;

import com.mvnnixbuyapi.gatewayservice.userservice.models.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
}
