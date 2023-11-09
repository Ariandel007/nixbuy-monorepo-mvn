package com.mvnnixbuyapi.gatewayservice.userservice.controllers;

import com.mvnnixbuyapi.gatewayservice.userservice.dto.AuthTokenDto;
import com.mvnnixbuyapi.gatewayservice.userservice.dto.LoginUserDto;
import com.mvnnixbuyapi.gatewayservice.userservice.services.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security")
public class LoginController {

    private final UserApplicationService userService;
    @Autowired
    public LoginController(UserApplicationService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/v1/authenticate", method = RequestMethod.POST)
    public ResponseEntity<AuthTokenDto> generateToken(@RequestBody LoginUserDto loginUser) throws AuthenticationException {
        return ResponseEntity.ok(this.userService.generateToken(loginUser));
    }

}
