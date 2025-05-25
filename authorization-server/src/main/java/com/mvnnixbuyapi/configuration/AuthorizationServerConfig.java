package com.mvnnixbuyapi.configuration;

import com.mvnnixbuyapi.clients.UserApplicationFeignClient;
import com.mvnnixbuyapi.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.time.Duration;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfig {
    @Value("${ISSUER_URI}")
    private String ISSUER_URI;

    @Value("${REDIRECT_URI}")
    private String REDIRECT_URI;

    private final UserApplicationFeignClient userApplicationFeignClient;

    public AuthorizationServerConfig(UserApplicationFeignClient userApplicationFeignClient) {
        this.userApplicationFeignClient = userApplicationFeignClient;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .clientAuthentication(authentication ->{
                                    authentication.authenticationConverter(new PublicClientRefreshTokenAuthenticationConverter());
                                    authentication.authenticationProvider(
                                            new PublicClientRefreshTokenAuthenticationProvider(
                                                    registeredClientRepository(),
                                                    new InMemoryOAuth2AuthorizationService() // replace with your AuthorizationService implementation
                                            ));
                                })
                                .oidc(Customizer.withDefaults())	// Enable OpenID Connect 1.0
                )
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Aquí lo hacemos stateless
                )
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                );

        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/webjars/**", "/images/**", "/css/**", "/assets/**", "/favicon.ico").permitAll()
                        .requestMatchers("/login").permitAll() // Permit login page and its query parameters
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureHandler(customAuthenticationFailureHandler())
                        .permitAll()
                )
                // For the Google Login, this server is going to be a OAuth2 Client
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/login").permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Aquí lo hacemos stateless
                );
        return http.build();
    }


    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("angular-client")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE) // PKCE no requiere secreto
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri(REDIRECT_URI) // Callback de Angular
                .scope(OidcScopes.OPENID)
                .scope("read")
                .clientSettings(ClientSettings.builder().requireProofKey(true).build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .refreshTokenTimeToLive(Duration.ofHours(2))
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer(ISSUER_URI) // Replace with your actual issuer URL
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        //Al final esto es lo que hara le diremos a Auth Server que verifique con las credenciales que se enviaron en su formulario
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder()); // Configura un encoder seguro
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userApplicationFeignClient);
    }

    @Bean
    OAuth2TokenGenerator<OAuth2Token> tokenGenerator(JwtEncoder jwtEncoder) {
        JwtGenerator jwtAccessTokenGenerator = new JwtGenerator(jwtEncoder);
        jwtAccessTokenGenerator.setJwtCustomizer(new JwtCustomizer(userApplicationFeignClient));
        return new DelegatingOAuth2TokenGenerator(jwtAccessTokenGenerator, new OAuth2PublicClientRefreshTokenGenerator());
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            if (exception instanceof UsernameNotFoundException) {
                response.sendRedirect("/login?error=credentials");
            } else if (exception instanceof BadCredentialsException) {
                response.sendRedirect("/login?error=credentials");
            } else {
                response.sendRedirect("/login?error=general");
            }
        };
    }

}
// STEP 1
//GET:
//http://localhost:3805/oauth2/authorize?
//response_type=code&
//client_id=angular-client&
//redirect_uri=http://localhost:4200/callback&
//scope=openid read&
//code_challenge=3UGX-Z-0o0ESWAUgBG5gkRxlp9AXZyj4ucJqM4LlMDU&
//code_challenge_method=S256

// STEP 2
//Exchange for an access token
//POST http://localhost:3805/oauth2/token
//Content-Type: application/x-www-form-urlencoded
//BODY
//grant_type=authorization_code
//client_id=angular-client
//redirect_uri=http://localhost:4200/callback
//code=CODE_OBTAINED_IN_STEP_1
//code_verifier=zhzOh7G8lDUllOBipu7vsh_Wxj4reJmgilrQDlhUyLI

// STEP 3
//Exchange for a Refresh token
//POST http://localhost:3805/oauth2/token
//Content-Type: application/x-www-form-urlencoded
//BODY
//grant_type=refresh_token
//client_id=angular-client
//refresh_token=REFRESH_TOKEN_OBTAINED_IN_STEP_2
