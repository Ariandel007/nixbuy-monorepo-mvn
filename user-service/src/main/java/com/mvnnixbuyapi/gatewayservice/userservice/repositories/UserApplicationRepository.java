package com.mvnnixbuyapi.gatewayservice.userservice.repositories;

import com.mvnnixbuyapi.gatewayservice.userservice.dto.UserToFindDto;
import com.mvnnixbuyapi.gatewayservice.userservice.models.UserApplication;
import com.mvnnixbuyapi.gatewayservice.userservice.utils.UserServiceQueries;
import com.mvnnixbuyapi.gatewayservice.userservice.repositories.custom.CustomUserApplicationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long>, CustomUserApplicationRepository {
    @Query(UserServiceQueries.selectUserToFindDto)
    UserToFindDto findUserToFindDto(@Param("userId") Long userId);

    Optional<UserApplication> findByUsername(String username);
}
