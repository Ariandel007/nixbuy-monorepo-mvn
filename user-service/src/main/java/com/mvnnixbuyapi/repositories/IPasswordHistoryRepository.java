package com.mvnnixbuyapi.repositories;

import com.mvnnixbuyapi.models.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
}
