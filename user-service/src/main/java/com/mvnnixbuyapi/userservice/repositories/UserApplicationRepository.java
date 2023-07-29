package com.mvnnixbuyapi.userservice.repositories;

import com.mvnnixbuyapi.userservice.dto.UserToFindDto;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import com.mvnnixbuyapi.userservice.repositories.custom.CustomUserApplicationRepository;
import com.mvnnixbuyapi.userservice.utils.UserServiceQueries;
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
