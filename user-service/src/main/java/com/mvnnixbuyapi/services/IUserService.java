package com.mvnnixbuyapi.services;

import com.mvnnixbuyapi.dto.UserRegisterDto;
import com.mvnnixbuyapi.dto.UserToFindDto;

public interface IUserService {
    UserRegisterDto registerUser(UserRegisterDto userRegisterDto);
    UserToFindDto findUserBasicInfoById(Long id);
}
