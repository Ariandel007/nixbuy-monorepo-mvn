package com.mvnnixbuyapi.commons.dtos.response;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class UserToLogin {
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean deleted = false;
    private boolean blocked = false;
    private String authType;
    private short attemps = 0;
    private String firstname;
    private String lastname;
    private String country;
    private String city;
    private Instant birthDate;
    private Instant accountCreationDate;
    private String photoUrl;
    private List<RoleApplicationLogin> roleApplicationList;
}
