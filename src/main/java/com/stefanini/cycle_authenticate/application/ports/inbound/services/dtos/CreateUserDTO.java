package com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos;


import java.time.LocalDate;

public record CreateUserDTO (
        String username,
        String email,
        String password,
        LocalDate dateOfBirth
){ }
