package com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateUserBodyDTO(
        @Schema(defaultValue = "jhon_doe", example = "jhon_doe", required = true)
        @NotBlank(message = "not blank username")
        String username,
        @Schema(defaultValue = "jhon_doe@example.com", example = "jhon_doe@example.com", required = true)
        @Email(message = "email invalid")
        @NotBlank(message = "not blank email")
        String email,
        @Schema(defaultValue = "Jhon_doe2006@2025", example = "Jhon_doe2006@2025", required = true,
                description = "password must contain a special character, uppercase or lowercase and a maximum of 20 characters",
                maxLength = 20)
        @NotBlank(message = "not blank password")
        @Size( max = 20)
        String password,
        @Schema(defaultValue = "2006-01-01", example = "2006-01-01", required = true)
        @NotNull(message = "date of birth required")
        LocalDate dateOfBirth
) {
}
