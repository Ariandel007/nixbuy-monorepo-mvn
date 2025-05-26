package com.mvnnixbuyapi.service;

import com.mvnnixbuyapi.clients.UserApplicationFeignClient;
import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.dtos.response.RoleApplicationLogin;
import com.mvnnixbuyapi.commons.dtos.response.UserToLogin;
import com.mvnnixbuyapi.model.CustomUserDetails;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.encrypt.BytesEncryptor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserApplicationFeignClient userApplicationFeignClient;
    private final BytesEncryptor bytesEncryptor;

    @Autowired
    public CustomUserDetailsService(UserApplicationFeignClient userApplicationFeignClient, BytesEncryptor bytesEncryptor) {
        this.userApplicationFeignClient = userApplicationFeignClient;
        this.bytesEncryptor = bytesEncryptor;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GenericResponseForBody<UserToLogin> user;
        try {
            user = this.userApplicationFeignClient.
                    findUserByUsername(username).getBody();
        } catch (FeignException.NotFound e) {
            throw new UsernameNotFoundException("User not found");
        }

        if(!user.getCode().contains("SUCCESSFUL")) {
            throw new UsernameNotFoundException("User not found");
        }

        var userFounded = user.getData();


        List<GrantedAuthority> authorities = userFounded.getRoleApplicationList().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new CustomUserDetails(
                userFounded.getId(),
                userFounded.getUsername(),
                userFounded.getPassword(),
                userFounded.getTwoFAActivated(),
                userFounded.getTwoFaRegistered(),
                userFounded.getSecurityQuestionEnabled(),
                userFounded.getSecurityQuestion(),
                userFounded.getAnswer(),
                userFounded.getMfaSecret(),
                userFounded.getMfaKeyId(),
                authorities
        );
    }

    public void saveUserInfoMfaRegistered(String secret, Long id) {
        String encryptedSecret = new String(Hex.encode(this.bytesEncryptor.encrypt(secret.getBytes(StandardCharsets.UTF_8))));
        this.userApplicationFeignClient.updateSecretMultiFactorSecret(id, encryptedSecret);
    }
}
