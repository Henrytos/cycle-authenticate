package com.stefanini.cycle_authenticate.infra.http.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticationBodyDTO(
        @Schema(defaultValue = "jhon_doe@example.com", example = "jhon_doe@example.com", required = true)
        String email,
        @Schema(defaultValue = "Jhon_doe2006@2025", example = "Jhon_doe2006@2025", required = true,
                description = "password must contain a special character, uppercase or lowercase and a maximum of 20 characters",
                maxLength = 20)
        String password) {
}
