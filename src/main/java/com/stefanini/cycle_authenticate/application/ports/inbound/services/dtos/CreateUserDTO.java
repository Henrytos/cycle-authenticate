package com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos;

import java.time.LocalDate;

public abstract class CreateUserDTO{
    String username ;
    String email;
    String password ;
    LocalDate date_of_brith ;
}
