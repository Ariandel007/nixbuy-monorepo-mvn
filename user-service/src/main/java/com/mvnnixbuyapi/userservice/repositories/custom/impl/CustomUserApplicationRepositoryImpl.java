package com.mvnnixbuyapi.userservice.repositories.custom.impl;

import com.mvnnixbuyapi.userservice.dto.UserDataWithRolesDto;
import com.mvnnixbuyapi.userservice.repositories.custom.CustomUserApplicationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
// El Impl es importante
@Repository
public class CustomUserApplicationRepositoryImpl implements CustomUserApplicationRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserDataWithRolesDto> listUserDataRoles(Long cursor) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT ");
        jpql.append("new com.mvnnixbuyapi.userservice.dto.UserDataWithRolesDto( ");
        jpql.append("u.id, u.username, u.blocked, ");
        jpql.append("r.name AS roles ");
        jpql.append(") ");
        jpql.append("FROM UserApplication u ");
        jpql.append("LEFT JOIN u.roleApplicationList r ");
        jpql.append("WHERE 1=1  ");
        if(cursor> 0L) {
            jpql.append("AND  u.id > :cursor ");
        }
        jpql.append("ORDER BY u.id ");

        TypedQuery<UserDataWithRolesDto> query = this.entityManager.createQuery(jpql.toString(), UserDataWithRolesDto.class);
        if(cursor> 0L) {
            query.setParameter("cursor", cursor);
        }
        return query.setMaxResults(10).getResultList();
    }
}
