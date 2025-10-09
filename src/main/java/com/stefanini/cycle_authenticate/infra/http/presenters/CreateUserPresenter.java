package com.stefanini.cycle_authenticate.infra.http.presenters;

import com.stefanini.cycle_authenticate.domain.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateUserPresenter(
        @Schema(defaultValue = "jhon_doe", description = "username user")
        String username,
        @Schema(defaultValue = "jhon_doe@example.com", description = "email user")
        String email
) {

    public static CreateUserPresenter toHttp(User user) {
        return new CreateUserPresenter(user.getUsername(), user.getEmail().getValue());
    }
}
