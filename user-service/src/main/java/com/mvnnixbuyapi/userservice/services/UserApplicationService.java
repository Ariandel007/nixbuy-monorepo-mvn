package com.mvnnixbuyapi.userservice.services;

import com.mvnnixbuyapi.userservice.dto.*;

public interface UserApplicationService {
    UserRegisterDto registerUser(UserRegisterDto userRegisterDto);
    UserToFindDto findUserBasicInfoById(Long id);
    UserDataToPasswordUpdatedDto updateUserPassword(Long userId, UserPasswordToUpdateDto userToUpdateDto);

    AuthTokenDto generateToken(LoginUserDto loginUser);


}
