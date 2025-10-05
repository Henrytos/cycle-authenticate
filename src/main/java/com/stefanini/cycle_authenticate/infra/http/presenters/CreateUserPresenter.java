package com.stefanini.cycle_authenticate.infra.http.presenters;

import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateUserDTO;
import com.stefanini.cycle_authenticate.domain.entities.User;

public record CreateUserPresenter(
        String username,
        String email
) {

    public static CreateUserPresenter toHttp(User user){
        return new CreateUserPresenter(user.getUsername(), user.getEmail().getValue());
    }
}
