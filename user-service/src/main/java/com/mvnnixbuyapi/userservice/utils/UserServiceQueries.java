package com.mvnnixbuyapi.userservice.utils;

public class UserServiceQueries {
    public static final String selectUserToFindDto =
            "SELECT new com.mvnnixbuyapi.userservice.dto.UserToFindDto( " +
            "u.id, u.username, u.country, u.city, u.birthDate, u.accountCreationDate, u.photoUrl " +
            ") " +
            "FROM UserApplication u WHERE u.id = :userId ";

    public static final String findUserByUsername =
            "SELECT u FROM UserApplication u JOIN FETCH u.roleApplicationList WHERE u.username = :username ";

    public static final String findUserByEmail =
            "SELECT u FROM UserApplication u JOIN FETCH u.roleApplicationList WHERE u.email = :email ";
}
