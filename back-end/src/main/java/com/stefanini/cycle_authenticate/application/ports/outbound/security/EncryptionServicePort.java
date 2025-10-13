package com.stefanini.cycle_authenticate.application.ports.outbound.security;

public interface EncryptionServicePort {
    String encode(String plan);
    Boolean match(String plan, String hash);
}
