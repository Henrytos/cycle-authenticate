package com.stefanini.cycle_authenticate.infra.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    private final String[] ROUTE_PERMIT = {
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui.html"
    };

    private final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/users",
            "/users/auth"
    };

    private final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/me"
    };

    private final String[] ENDPOINTS_ADMIN = {
        "/admin/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(HttpMethod.POST, ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                            .requestMatchers(this.ROUTE_PERMIT).permitAll()
                            .requestMatchers(this.ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                            .requestMatchers(this.ENDPOINTS_ADMIN).hasRole("ADMIN")
                            .anyRequest().denyAll();
                });

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
