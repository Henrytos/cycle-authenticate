package com.stefanini.cycle_authenticate.infra.adapters.outbound.security;

import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.SessionTokenDTO;

public class SessionTokenResponseDTO extends SessionTokenDTO {
    
    public SessionTokenResponseDTO(String token, Long expireAt) {
        super(token, expireAt);
    }
}
