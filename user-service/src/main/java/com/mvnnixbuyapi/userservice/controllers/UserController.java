package com.mvnnixbuyapi.userservice.controllers;

import com.mvnnixbuyapi.userservice.dto.UserRegisterDto;
import com.mvnnixbuyapi.userservice.dto.UserToFindDto;
import com.mvnnixbuyapi.userservice.services.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserApplicationService userService;

    @Autowired
    public UserController(UserApplicationService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register-user", headers = "X-API-Version=1")
    ResponseEntity<UserRegisterDto> registerUserV1(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok().body(this.userService.registerUser(userRegisterDto));
    }

    @GetMapping(value = "/basic-user-info/{userId}", headers = "X-API-Version=1")
    ResponseEntity<UserToFindDto> findUserBasicInfoById(@PathVariable Long userId) {
        return ResponseEntity.ok().body(this.userService.findUserBasicInfoById(userId));
    }

}
