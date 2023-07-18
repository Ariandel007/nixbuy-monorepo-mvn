package com.mvnnixbuyapi.controllers;

import com.mvnnixbuyapi.dto.UserRegisterDto;
import com.mvnnixbuyapi.dto.UserToFindDto;
import com.mvnnixbuyapi.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
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
