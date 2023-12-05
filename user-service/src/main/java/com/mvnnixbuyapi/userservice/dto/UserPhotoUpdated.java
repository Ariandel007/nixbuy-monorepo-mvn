package com.mvnnixbuyapi.userservice.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserPhotoUpdated {
    private Long id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String country;
    private String city;
    private Instant birthDate;
    private Instant accountCreationDate;
    private String photoUrl;

}
