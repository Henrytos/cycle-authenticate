package com.stefanini.cycle_authenticate.application.ports.inbound.services;

import com.stefanini.cycle_authenticate.application.exceptions.UserNotFoundException;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateUserBodyDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.SessionTokenDTO;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import com.stefanini.cycle_authenticate.domain.value_objects.Password;


public interface UserServicePort {
    User create(CreateUserBodyDTO createUserDTO) throws UserNotFoundException;
    SessionTokenDTO authenticate(Email email, Password password) throws UserNotFoundException;
    User getProfile(String username) throws UserNotFoundException;
}
