package com.mvnnixbuyapi.userservice.repositories;

import com.mvnnixbuyapi.userservice.models.RoleApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleApplicationRepository extends JpaRepository<RoleApplication, Long> {
}
