package com.mvnnixbuyapi.userservice.services;

import com.mvnnixbuyapi.commons.dto.UserRegisterDto;
import com.mvnnixbuyapi.commons.dto.UserToFindDto;

public interface UserService {
    UserRegisterDto registerUser(UserRegisterDto userRegisterDto);
    UserToFindDto findUserBasicInfoById(Long id);
}
