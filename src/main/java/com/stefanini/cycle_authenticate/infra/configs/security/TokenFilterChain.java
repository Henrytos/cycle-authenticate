package com.stefanini.cycle_authenticate.infra.configs.security;

import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories.UserRepositoryAdapter;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.security.SessionTokenServiceAdapter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilterChain extends OncePerRequestFilter {

    @Autowired
    private SessionTokenServiceAdapter sessionTokenServiceAdapter;

    @Autowired
    private UserRepositoryAdapter userRepositoryAdapter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("passou no filter");

        filterChain.doFilter(request, response);
    }
}
