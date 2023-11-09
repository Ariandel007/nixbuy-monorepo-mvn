package com.mvnnixbuyapi.gatewayservice.userservice.repositories.custom;

import com.mvnnixbuyapi.gatewayservice.userservice.dto.UserDataWithRolesDto;

import java.util.List;

public interface CustomUserApplicationRepository {
    List<UserDataWithRolesDto> listUserDataRoles();
}
