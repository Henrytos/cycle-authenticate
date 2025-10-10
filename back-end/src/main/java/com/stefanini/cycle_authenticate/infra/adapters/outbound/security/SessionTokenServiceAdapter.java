package com.stefanini.cycle_authenticate.infra.adapters.outbound.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.stefanini.cycle_authenticate.application.ports.outbound.security.SessionTokenServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class SessionTokenServiceAdapter implements SessionTokenServicePort<DecodedJWT> {

    @Value("{spring.jwt.secret.key}")
    private String jwtSecretKey;

    @Override
    public SessionTokenResponseDTO generator(UUID identifier) {
        Algorithm algorithm = Algorithm.HMAC256(this.jwtSecretKey);

        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(10));

        String token = JWT.create()
                .withIssuer("cycle authenticate enterprise")
                .withSubject(identifier.toString())
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return new SessionTokenResponseDTO(token, expiresAt.toEpochMilli());
    }

    @Override
    public DecodedJWT validate(String token) {
        Algorithm algorithm = Algorithm.HMAC256(this.jwtSecretKey);

        try {
            return JWT.require(algorithm).build().verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}


