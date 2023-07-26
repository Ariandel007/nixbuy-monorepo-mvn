package com.mvnnixbuyapi.userservice.repositories;

import com.mvnnixbuyapi.userservice.models.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
}
