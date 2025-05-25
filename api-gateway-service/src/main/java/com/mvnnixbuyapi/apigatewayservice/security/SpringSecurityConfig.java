package com.mvnnixbuyapi.apigatewayservice.security;

import com.mvnnixbuyapi.apigatewayservice.exceptions.CustomAccessDeniedHandler;
import com.mvnnixbuyapi.apigatewayservice.exceptions.CustomAuthenticationEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

/**En spring boot 3.1.0 parece que es necesario usar el @Configuration ya que @EnableWebFluxSecurity no creara los beans
  por si solo
  **/
@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {
    Logger logger = LoggerFactory.getLogger(SpringSecurityConfig.class);

     @Value("${ISSUER_URI}") // Set this to the Authorization Server's issuer URI
     private String issuerUri;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SpringSecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                                CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

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
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder())) // Configure JWT decoder
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .build();
    }

     @Bean
     public ReactiveJwtDecoder jwtDecoder() {
         // Automatically discovers the JWK URI from the issuer's well-known metadata
        //http://localhost:3805/.well-known/openid-configuration
         return ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
     }

    private Mono<AuthorizationDecision> currentUserIdMatchesPath(Mono<Authentication> authentication, AuthorizationContext context) {
        return authentication
                .map(auth -> {
                    if (auth.isAuthenticated() && auth.getPrincipal() instanceof Jwt) {
                        Jwt jwt = (Jwt) auth.getPrincipal();
                        String userIdFromToken = jwt.getClaimAsString("user_id");
                        String userIdFromPath = context.getVariables().get("userId").toString();

                        return userIdFromPath.equals(userIdFromToken) ||
                                auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                    }
                    return false;
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

 }
