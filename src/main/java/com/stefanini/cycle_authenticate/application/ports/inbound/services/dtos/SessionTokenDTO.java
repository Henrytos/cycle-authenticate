package com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos;

public class SessionTokenDTO {

    private String token;

    private Long expireAt ;

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
