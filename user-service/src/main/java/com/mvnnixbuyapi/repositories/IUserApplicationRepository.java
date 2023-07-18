package com.mvnnixbuyapi.repositories;

import com.mvnnixbuyapi.dto.UserToFindDto;
import com.mvnnixbuyapi.models.UserApplication;
import com.mvnnixbuyapi.utils.Queries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserApplicationRepository extends JpaRepository<UserApplication, Long> {
    @Query(Queries.selectUserToFindDto)
    UserToFindDto findUserToFindDto(@Param("userId") Long userId);
}
