package com.mvnnixbuyapi.clients;

import com.mvnnixbuyapi.commons.dtos.request.UserToCreateAuth;
import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.dtos.response.UserToLogin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service-nixbuy", url = "${users-url}")
public interface UserApplicationFeignClient {
    @GetMapping(value = "/api/users/v1/users-app/{username}", produces = "application/json")
    ResponseEntity<GenericResponseForBody<UserToLogin>> findUserByUsername(@PathVariable String username);

    @GetMapping(value = "/api/users/v1/users-app/email/{email}", produces = "application/json")
    ResponseEntity<GenericResponseForBody<UserToLogin>> findUserByEmail(@PathVariable String email);

    @PostMapping(value = "/api/users/v1/users-app/oidc-user", produces = "application/json")
    ResponseEntity<GenericResponseForBody<UserToLogin>> createUserFromOidcUser(@RequestBody UserToCreateAuth userToCreateAuth);

    @PatchMapping (value = "/api/users/v1/users-app/info-mfa/{userId}")
    ResponseEntity<GenericResponseForBody<UserToLogin>> updateSecretMultiFactorSecret(
            @PathVariable Long userId,
            @RequestBody String secret);
}
