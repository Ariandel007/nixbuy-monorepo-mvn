package com.mvnnixbuyapi.userservice.repositories;

import com.mvnnixbuyapi.userservice.dto.UserToFindDto;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import com.mvnnixbuyapi.userservice.utils.UserServiceQueries;
import com.mvnnixbuyapi.userservice.repositories.custom.CustomUserApplicationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long>, CustomUserApplicationRepository {
    @Query(UserServiceQueries.selectUserToFindDto)
    UserToFindDto findUserToFindDto(@Param("userId") Long userId);

    @Query(UserServiceQueries.findUserByUsername)
    Optional<UserApplication> findByUsername(@Param("username") String username);

    @Query(UserServiceQueries.findUserByEmail)
    Optional<UserApplication> findByEmail(@Param("email") String email);

}
