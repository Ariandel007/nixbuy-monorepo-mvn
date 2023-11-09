package com.mvnnixbuyapi.gatewayservice.userservice.repositories;

import com.mvnnixbuyapi.gatewayservice.userservice.models.RoleApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleApplicationRepository extends JpaRepository<RoleApplication, Long> {
}
