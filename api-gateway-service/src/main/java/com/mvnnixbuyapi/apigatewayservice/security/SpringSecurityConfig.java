package com.mvnnixbuyapi.apigatewayservice.security;

import com.mvnnixbuyapi.apigatewayservice.filters.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

 /**En spring boot 3.1.0 parece que es necesario usar el @Configuration ya que @EnableWebFluxSecurity no creara los beans
  por si solo
  **/
@Configuration
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
                        //Actuator
                        .pathMatchers("/actuator/**").permitAll()
                        // User Service
                        .pathMatchers(HttpMethod.PATCH,
                                "/api/user-service-nixbuy/users/v1/update-user-info/{userId}",
                                "/api/user-service-nixbuy/users/v1/update-photo-url/{userId}",
                                "/api/user-service-nixbuy/users/v1/update-user-password/{userId}"
                        ).access(this::currentUserIdMatchesPath)
                        .pathMatchers(HttpMethod.GET,
                                "/api/user-service-nixbuy/users/v1/basic-user-info/{userId}"
                        ).access(this::currentUserIdMatchesPath)
                        .pathMatchers(HttpMethod.GET,"/api/user-service-nixbuy/users/v1/find-users-list/**").hasAnyAuthority("ROLE_ADMIN")
                        .pathMatchers(HttpMethod.POST,"/api/user-service-nixbuy/users/v1/register-user").hasAnyAuthority("ROLE_ADMIN")
                        .pathMatchers("/api/user-service-nixbuy/users/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .pathMatchers(HttpMethod.POST, "/api/user-service-nixbuy/security/**").permitAll()
                        // Product Service
                        .pathMatchers(HttpMethod.POST,"/api/products-service-nixbuy/**").hasAnyAuthority("ROLE_ADMIN")
                        .pathMatchers(HttpMethod.PATCH,"/api/products-service-nixbuy/**").hasAnyAuthority("ROLE_ADMIN")
                        // Payment Service
                        .pathMatchers(HttpMethod.POST,
                                "/api/payment-service-nixbuy/payment/v1/create-order/{userId}",
                                "/api/payment-service-nixbuy/payment/stripe/v1/checkout/hosted/{userId}"
                        ).access(this::currentUserIdMatchesPath)
                        .pathMatchers(HttpMethod.POST,
                                "/api/payment-service-nixbuy/payment/v1/get-order/**"
                        ).hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                        .anyExchange()
                        .authenticated()
                )
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }


    private Mono<AuthorizationDecision> currentUserMatchesPath(Mono<Authentication> authentication, AuthorizationContext context) {
        return authentication
                .map(auth -> context.getVariables().get("username").equals(auth.getName())
                        || auth.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN"))
                )
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
