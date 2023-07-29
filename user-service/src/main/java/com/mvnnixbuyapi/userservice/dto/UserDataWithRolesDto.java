package com.mvnnixbuyapi.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataWithRolesDto {
    private Long id;
    private String username;
    private boolean isBlocked;
    private String roles;
}
