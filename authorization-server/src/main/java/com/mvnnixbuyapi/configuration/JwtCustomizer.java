package com.mvnnixbuyapi.configuration;

import com.mvnnixbuyapi.clients.UserApplicationFeignClient;
import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.dtos.response.RoleApplicationLogin;
import com.mvnnixbuyapi.commons.dtos.response.UserToLogin;
import com.mvnnixbuyapi.model.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final UserApplicationFeignClient userApplicationFeignClient;

    @Autowired
    public JwtCustomizer(UserApplicationFeignClient userApplicationFeignClient) {
        this.userApplicationFeignClient = userApplicationFeignClient;
    }

    @Override
    public void customize(JwtEncodingContext context) {
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            // Extract roles from authentication (CustomUserDetailsService)
            var authentication = context.getPrincipal();
            if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
                var roles = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());

                context.getClaims()
                        .claim("roles", roles)
                        .claim("user_id", userDetails.getId()); // Extrae el ID directamente
            } else if (authentication.getPrincipal() instanceof DefaultOidcUser oidcUser) { // oauth2 login
                // fetch user by email to obtain User object when principal is not already a User object, here's lets gonna asume is google
                String email = oidcUser.getEmail();
                // Check if user exists in database
                GenericResponseForBody<UserToLogin> userFromBd = this.userApplicationFeignClient.
                        findUserByEmail(email).getBody();

                // If user does not exist, create a new user with that email
                if (userFromBd.getCode().equals("USER_NOT_FOUND")) {

                }

                var userFounded = userFromBd.getData();
                Set<String> roles = userFounded.getRoleApplicationList().stream()
                        .map(RoleApplicationLogin::getName)
                        .collect(Collectors.toSet());
                context.getClaims().claim("roles", roles);
            }
        }
    }
}

//http://localhost:8080/oauth2/jwks


//http://localhost:8080/.well-known/openid-configuration
//This is the standard URL for OpenID Connect discovery, and it contains all the necessary endpoints, including the URL to retrieve the JWK Set (/oauth2/jwks).
