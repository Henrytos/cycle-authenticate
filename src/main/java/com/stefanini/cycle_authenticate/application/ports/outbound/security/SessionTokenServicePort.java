package com.stefanini.cycle_authenticate.application.ports.outbound.security;

import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.SessionTokenDTO;

import java.util.UUID;

public interface SessionTokenServicePort {

    SessionTokenDTO generator(UUID identifier);
    Boolean validate(String token);

}
