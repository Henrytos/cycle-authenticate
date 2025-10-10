package com.stefanini.cycle_authenticate.infra.adapters.inbound.services;

import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories.JpaUserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserModelDetailsService implements UserDetailsService {

    @Autowired
    private JpaUserModelRepository jpaUserModelRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.jpaUserModelRepository.findByUsername(username).orElse(null);
    }
}
