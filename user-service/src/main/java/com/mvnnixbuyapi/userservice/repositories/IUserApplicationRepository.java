package com.mvnnixbuyapi.userservice.repositories;

import com.mvnnixbuyapi.commons.dto.UserToFindDto;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import com.mvnnixbuyapi.userservice.repositories.custom.ICustomUserApplicationRepository;
import com.mvnnixbuyapi.userservice.utils.Queries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserApplicationRepository extends JpaRepository<UserApplication, Long>, ICustomUserApplicationRepository {
    @Query(Queries.selectUserToFindDto)
    UserToFindDto findUserToFindDto(@Param("userId") Long userId);
}
