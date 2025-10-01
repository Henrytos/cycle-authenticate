package com.stefanini.cycle_authenticate.infra.adapters.outbound.security;

import com.stefanini.cycle_authenticate.application.ports.outbound.security.EncryptionServicePort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceAdapter implements EncryptionServicePort {

    private final PasswordEncoder passwordEncoder;

    public EncryptionServiceAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String plan) {
        return this.passwordEncoder.encode(plan);
    }

    @Override
    public Boolean match(String plan, String hash) {
        return this.passwordEncoder.matches(plan, hash);
    }
}
