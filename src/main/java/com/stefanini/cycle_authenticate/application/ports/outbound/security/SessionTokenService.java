package com.stefanini.cycle_authenticate.application.ports.outbound.security;

import java.util.UUID;

public interface SessionTokenService {

    String generator(UUID identifier);
    Boolean validate(String token);

}
