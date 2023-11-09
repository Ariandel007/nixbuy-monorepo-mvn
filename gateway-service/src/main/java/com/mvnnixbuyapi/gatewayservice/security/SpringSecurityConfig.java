package com.mvnnixbuyapi.gatewayservice.security;

import com.mvnnixbuyapi.gatewayservice.filter.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

import java.util.Map;


@EnableWebFluxSecurity
public class SpringSecurityConfig {
    Logger logger = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorize -> authorize
                                .pathMatchers(HttpMethod.POST, "/api/**").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }


    private Mono<AuthorizationDecision> currentUserMatchesPath(Mono<Authentication> authentication, AuthorizationContext context) {
        return authentication
                .map(auth -> context.getVariables().get("username").equals(auth.getName()) || auth.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN")))
                .map((granted) -> new AuthorizationDecision(granted))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    private Mono<AuthorizationDecision> currentUserIdMatchesPath(Mono<Authentication> authentication, AuthorizationContext context) {
        return authentication
                .map(auth -> {
                    if(auth.isAuthenticated()) {
                        Map<String, String> details = (Map<String, String>) auth.getDetails();
                        return (context.getVariables().get("userId").equals(details.get("id_user"))
                                ||
                                auth.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN")));
                    }
                    return false;
                })
                .map((granted) -> new AuthorizationDecision(granted))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
