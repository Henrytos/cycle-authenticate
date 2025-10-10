package com.stefanini.cycle_authenticate.infra.configs.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.mappers.UserMapper;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.UserModel;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories.UserRepositoryAdapter;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.security.SessionTokenServiceAdapter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenFilterChain extends OncePerRequestFilter {

    @Autowired
    private SessionTokenServiceAdapter sessionTokenServiceAdapter;

    @Autowired
    private UserRepositoryAdapter userRepositoryAdapter;

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        List<String> routesPermit = List.of(SpringSecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED);

        if (header != null && !routesPermit.contains(request.getRequestURI())) {
            String token = header.replaceAll("Bearer ", "");

            DecodedJWT decodedJWT = this.sessionTokenServiceAdapter.validate(token);
            UUID userId = UUID.fromString(decodedJWT.getSubject());

            Optional<User> user = this.userRepositoryAdapter.findById(userId);

            if (user.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            UserModel userModel = this.userMapper.toModel(user.get());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userModel.getUsername(), null, userModel.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
