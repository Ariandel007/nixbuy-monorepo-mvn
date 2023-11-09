package com.mvnnixbuyapi.gatewayservice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationManagerJwt implements ReactiveAuthenticationManager {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        //just convierte un objeto normal a uno reactivo, en este caso el token que se obtendra del JwtAuthenticationfilter
        return Mono.just(authentication.getCredentials().toString())
                .map(token-> tokenProvider.getAllClaimsFromToken(token))
                .map(claims -> {//con map tendremos los claims del token
                    String userName = claims.get("user_name", String.class);
                    List<String> roles = claims.get("authorities", List.class);

                    Collection<GrantedAuthority> authorities = roles.stream().map(role->
                            new SimpleGrantedAuthority(role)).collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userName, null, authorities);

                    HashMap<String,String> details = new HashMap<>();
                    details.put("id_user", claims.get("id_user", String.class));
                    usernamePasswordAuthenticationToken.setDetails(details);
                    return usernamePasswordAuthenticationToken;
                });
    }
}
