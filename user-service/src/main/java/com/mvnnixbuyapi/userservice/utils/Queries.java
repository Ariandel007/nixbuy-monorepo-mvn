package com.mvnnixbuyapi.userservice.utils;

public class Queries {
    public static final String selectUserToFindDto =
            "SELECT new com.mvnnixbuyapi.commons.dto.UserToFindDto( " +
            "u.id, u.username, u.country, u.city, u.birthDate, u.accountCreationDate, u.photoUrl " +
            ") " +
            "FROM UserApplication u WHERE u.id = :userId ";

}
