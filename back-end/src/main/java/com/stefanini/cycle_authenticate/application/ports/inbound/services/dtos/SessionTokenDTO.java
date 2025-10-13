package com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public class SessionTokenDTO {

    @Schema(description = "token", defaultValue = "wuoefncsobcew830ni")
    private String token;

    @Schema(description = "expireAt", defaultValue = "1334283420472480")
    private Long expireAt ;

    public SessionTokenDTO() {
    }

    public SessionTokenDTO(String token, Long expireAt) {
        this.token = token;
        this.expireAt = expireAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }
}
