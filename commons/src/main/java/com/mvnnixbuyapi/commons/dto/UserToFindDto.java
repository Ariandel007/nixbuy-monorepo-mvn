package com.mvnnixbuyapi.commons.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserToFindDto {
    private Long id;
    private String username;
    private String country;
    private String city;
    private Instant birthDate;
    private Instant accountCreationDate;
    private String photoUrl;
}
