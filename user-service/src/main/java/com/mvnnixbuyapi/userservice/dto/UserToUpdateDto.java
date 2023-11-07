package com.mvnnixbuyapi.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToUpdateDto {
    private Long id;
    private String country;
    private String city;
    private Instant birthDate;
    private Instant accountCreationDate;
    private String firstname;
    private String lastname;

}
