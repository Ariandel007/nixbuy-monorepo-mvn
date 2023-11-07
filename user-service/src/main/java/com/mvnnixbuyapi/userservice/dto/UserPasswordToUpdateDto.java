package com.mvnnixbuyapi.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordToUpdateDto {
    private Long id;
    private String password;

}
