package com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record CreateUserDTO(
        @Schema(defaultValue = "jhon_doe", example = "jhon_doe", required = true)
        String username,
        @Schema(defaultValue = "jhon_doe@example.com", example = "jhon_doe@example.com", required = true)
        String email,
        @Schema(defaultValue = "Jhon_doe2006@2025", example = "Jhon_doe2006@2025", required = true,
                description = "password must contain a special character, uppercase or lowercase and a maximum of 20 characters",
                maxLength = 20)
        String password,
        @Schema(defaultValue = "2006-01-01", example = "2006-01-01", required = true)
        LocalDate dateOfBirth
) {
}
