package com.mvnnixbuyapi.commons.dto;

import lombok.Data;

@Data
public class UserDataWithRolesDto {
    private Long id;
    private String username;
    private boolean isBlocked;
    private String roles;
}
