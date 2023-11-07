package com.mvnnixbuyapi.userservice.controllers;

import com.mvnnixbuyapi.userservice.dto.*;
import com.mvnnixbuyapi.userservice.services.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return null;
    }

    @PatchMapping(value = "/v1/update-photo-url/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserToUpdateDto> updatePhotoV1(@PathVariable Long userId,
                                                  @RequestPart(value = "file") MultipartFile file) {
        return null;
    }

    @PatchMapping(value = "/v1/update-user-password/{userId}")
    ResponseEntity<UserDataToPasswordUpdatedDto> updateUserPasswordV1(
            @PathVariable Long userId,
            @RequestBody UserPasswordToUpdateDto userToUpdateDto) {
        return null;
    }
}
