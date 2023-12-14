package com.mvnnixbuyapi.userservice.services;

import com.mvnnixbuyapi.userservice.dto.*;
import com.mvnnixbuyapi.userservice.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserApplicationService {
    UserRegisterDto registerUser(UserRegisterDto userRegisterDto);
    UserToFindDto findUserBasicInfoById(Long id);
    UserToUpdateDto updateUserBasicInformation(Long userId, UserToUpdateDto userToUpdateDto);
    UserDataToPasswordUpdatedDto updateUserPassword(Long userId, UserPasswordToUpdateDto userToUpdateDto);

    AuthTokenDto generateToken(LoginUserDto loginUser);

    UserPhotoUpdated uploadPhoto(Long userId, MultipartFile filePhoto);

    List<UserDataWithRolesDto> listUserDataWithRolesDtos(Long cursorId);
}
