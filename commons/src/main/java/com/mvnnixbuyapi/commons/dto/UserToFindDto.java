package com.mvnnixbuyapi.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToFindDto {
    private Long id;
    private String username;
    private String country;
    private String city;
    private Instant birthDate;
    private Instant accountCreationDate;
    private String photoUrl;
}
