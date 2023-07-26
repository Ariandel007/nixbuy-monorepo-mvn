package com.mvnnixbuyapi.userservice.repositories.custom.impl;

import com.mvnnixbuyapi.commons.dto.UserDataWithRolesDto;
import com.mvnnixbuyapi.userservice.repositories.custom.ICustomUserApplicationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomUserApplicationRepository implements ICustomUserApplicationRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserDataWithRolesDto> findUserDataRoles() {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT new com.mvnnixbuyapi.commons.dto.UserDataWithRolesDto( ");
        jpql.append("u.id, u.username, u.isBlocked, ");
        jpql.append("GROUP_CONCAT(DISTINCT r.name SEPARATOR ',') AS roles ");
        jpql.append(") ");
        jpql.append("FROM UserApplication u ");
        jpql.append("LEFT JOIN FETCH u.roleApplicationList r ");
        jpql.append("WHERE 1=1  ");
//        if(cursor> 0L) {
//            jpql.append("AND  u.id > :cursor ");
//        }
        jpql.append("GROUP BY u.id, u.username, u.isBlocked ");
        jpql.append("ORDER BY u.id ");

        TypedQuery<UserDataWithRolesDto> query = this.entityManager.createQuery(jpql.toString(), UserDataWithRolesDto.class);
//        if(cursor> 0L) {
//            query.setParameter("cursor", cursor);
//        }
        return query.setMaxResults(10).getResultList();
    }
}
