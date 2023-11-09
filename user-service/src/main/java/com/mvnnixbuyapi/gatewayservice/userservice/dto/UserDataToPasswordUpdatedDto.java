package com.mvnnixbuyapi.gatewayservice.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataToPasswordUpdatedDto {
    private Long id;
    private String username;
}
