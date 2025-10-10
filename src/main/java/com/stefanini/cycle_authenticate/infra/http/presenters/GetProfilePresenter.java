package com.stefanini.cycle_authenticate.infra.http.presenters;

import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record GetProfilePresenter(
        @Schema(example = "jhon_doe")
        String username,
        @Schema(example = "jhon_doe@example.com")
        String email,
        @Schema(examples = {"ROLE_USER", "ROLE_ADMIN"})
        UserRole userRole,
        @Schema(example = "2006-01-01")
        LocalDate dateOfBirth) {

    public static GetProfilePresenter toHttp(User user) {
        return new GetProfilePresenter(user.getUsername(), user.getEmail().getValue(), user.getUserRole(), user.getDateOfBrith());
    }
}
