package com.mvnnixbuyapi.userservice.repositories.custom;

import com.mvnnixbuyapi.userservice.dto.UserDataWithRolesDto;

import java.util.List;

public interface CustomUserApplicationRepository {
    List<UserDataWithRolesDto> listUserDataRoles();
}
