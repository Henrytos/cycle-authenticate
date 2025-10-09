package com.stefanini.cycle_authenticate.infra.http.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationBodyDTO(
        @Schema(defaultValue = "jhon_doe@example.com", example = "jhon_doe@example.com", required = true)
        @Email(message = "email invalid")
        String email,
        @Schema(defaultValue = "Jhon_doe2006@2025", example = "Jhon_doe2006@2025", required = true,
                description = "password must contain a special character, uppercase or lowercase and a maximum of 20 characters",
                maxLength = 20)
        @NotBlank(message = "not blank password")
        @Size( max = 20)
        String password) {
}
