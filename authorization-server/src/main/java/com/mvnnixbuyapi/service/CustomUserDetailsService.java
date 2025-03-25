package com.mvnnixbuyapi.service;

import com.mvnnixbuyapi.clients.UserApplicationFeignClient;
import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.dtos.response.RoleApplicationLogin;
import com.mvnnixbuyapi.commons.dtos.response.UserToLogin;
import com.mvnnixbuyapi.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserApplicationFeignClient userApplicationFeignClient;

    @Autowired
    public CustomUserDetailsService(UserApplicationFeignClient userApplicationFeignClient) {
        this.userApplicationFeignClient = userApplicationFeignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GenericResponseForBody<UserToLogin> user = this.userApplicationFeignClient.
                findUserByUsername(username).getBody();

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
                authorities
        );
    }
}
