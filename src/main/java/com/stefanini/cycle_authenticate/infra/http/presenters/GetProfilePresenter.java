package com.stefanini.cycle_authenticate.infra.http.presenters;

import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.UserRole;

import java.time.LocalDate;

public record GetProfilePresenter(
        String username,
        String email,
        UserRole userRole,
        LocalDate dateOfBirth) {

    public static GetProfilePresenter toHttp(User user) {
        return new GetProfilePresenter(user.getUsername(), user.getEmail().getValue(), user.getUserRole(), user.getDateOfBrith());
    }
}
