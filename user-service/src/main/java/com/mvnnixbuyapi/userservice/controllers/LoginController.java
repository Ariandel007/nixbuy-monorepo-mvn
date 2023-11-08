package com.mvnnixbuyapi.userservice.controllers;

import com.mvnnixbuyapi.userservice.components.TokenProvider;
import com.mvnnixbuyapi.userservice.dto.AuthTokenDto;
import com.mvnnixbuyapi.userservice.dto.LoginUserDto;
import com.mvnnixbuyapi.userservice.services.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
