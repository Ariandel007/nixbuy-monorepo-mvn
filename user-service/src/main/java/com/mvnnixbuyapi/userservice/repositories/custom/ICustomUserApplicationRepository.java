package com.mvnnixbuyapi.userservice.repositories.custom;

import com.mvnnixbuyapi.commons.dto.UserDataWithRolesDto;

import java.util.List;

public interface ICustomUserApplicationRepository {
    List<UserDataWithRolesDto> findUserDataRoles();
}
