package com.mvnnixbuyapi.userservice.services;

import com.mvnnixbuyapi.userservice.dto.UserRegisterDto;
import com.mvnnixbuyapi.userservice.dto.UserToFindDto;

public interface UserApplicationService {
    UserRegisterDto registerUser(UserRegisterDto userRegisterDto);
    UserToFindDto findUserBasicInfoById(Long id);
}
