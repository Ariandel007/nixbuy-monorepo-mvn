package com.mvnnixbuyapi.gatewayservice.userservice.utils;

public class UserServiceQueries {
    public static final String selectUserToFindDto =
            "SELECT new com.mvnnixbuyapi.userservice.dto.UserToFindDto( " +
            "u.id, u.username, u.country, u.city, u.birthDate, u.accountCreationDate, u.photoUrl " +
            ") " +
            "FROM UserApplication u WHERE u.id = :userId ";

}
