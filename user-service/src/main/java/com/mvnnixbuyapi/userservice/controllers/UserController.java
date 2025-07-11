package com.mvnnixbuyapi.userservice.controllers;

import com.mvnnixbuyapi.commons.dtos.request.UserToCreateAuth;
import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.dtos.response.UserToLogin;
import com.mvnnixbuyapi.commons.utils.ResponseUtils;
import com.mvnnixbuyapi.userservice.dto.*;
import com.mvnnixbuyapi.userservice.services.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserApplicationService userService;

    @Autowired
    public UserController(UserApplicationService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/v1/register-user")
    ResponseEntity<UserRegisterDto> registerUserV1(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok().body(this.userService.registerUser(userRegisterDto));
    }

    @GetMapping(value = "/v1/basic-user-info/{userId}")
    ResponseEntity<UserToFindDto> findUserBasicInfoByIdV1(@PathVariable Long userId) {
        return ResponseEntity.ok().body(this.userService.findUserBasicInfoById(userId));
    }

    @PatchMapping(value = "/v1/update-user-info/{userId}")
    ResponseEntity<UserToUpdateDto> updateUserV1(@PathVariable Long userId,
                                                 @RequestBody UserToUpdateDto userToUpdateDto) {
        return ResponseEntity.ok().body(this.userService.updateUserBasicInformation(userId, userToUpdateDto));
    }

    @PatchMapping(value = "/v1/update-photo-url/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserPhotoUpdated> updatePhotoV1(@PathVariable Long userId,
                                                  @RequestPart(value = "userPhotoFile") MultipartFile userPhotoFile) {
        return ResponseEntity.ok().body(this.userService.uploadPhoto(userId, userPhotoFile));
    }

    @PatchMapping(value = "/v1/update-user-password/{userId}")
    ResponseEntity<UserDataToPasswordUpdatedDto> updateUserPasswordV1(
            @PathVariable Long userId,
            @RequestBody UserPasswordToUpdateDto userToUpdateDto) {
        return ResponseEntity.ok().body(this.userService.updateUserPassword(userId, userToUpdateDto));
    }

    @GetMapping(value = "/v1/find-users-list/{cursorId}")
    ResponseEntity<List<UserDataWithRolesDto>> findUsersList(@PathVariable Long cursorId) {
        return ResponseEntity.ok().body(this.userService.listUserDataWithRolesDtos(cursorId));
    }

    @GetMapping(value = "/v1/users-app/{username}")
    ResponseEntity<GenericResponseForBody<UserToLogin>> findUserByUsername(@PathVariable String username) {
        return ResponseUtils.buildSuccessResponse(this.userService.findUserByUsername(username));
    }

    @GetMapping(value = "/v1/users-app/email/{email}")
    ResponseEntity<GenericResponseForBody<UserToLogin>> findUserByEmail(@PathVariable String email) {
        return ResponseUtils.buildSuccessResponse(this.userService.findUserByEmail(email));
    }

    @PostMapping (value = "/v1/users-app/oidc-user")
    ResponseEntity<GenericResponseForBody<UserToLogin>> createUserFromOidcUser(@RequestBody UserToCreateAuth userToCreateAuth) {
        return ResponseUtils.buildSuccessResponse(this.userService.createUserFromOidcUser(userToCreateAuth));
    }
}
