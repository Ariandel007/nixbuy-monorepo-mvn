package com.mvnnixbuyapi.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserRegisterDto {
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String country;
    private String city;
    private Instant birthDate;

}
