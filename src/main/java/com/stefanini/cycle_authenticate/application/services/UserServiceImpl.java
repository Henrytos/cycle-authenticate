package com.stefanini.cycle_authenticate.application.services;

import com.stefanini.cycle_authenticate.application.exceptions.InputInvalidException;
import com.stefanini.cycle_authenticate.application.exceptions.UserAlreadyExistsException;
import com.stefanini.cycle_authenticate.application.exceptions.UserNotFoundException;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.UserServicePort;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateUserDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.SessionTokenDTO;
import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.UserRepositoryPort;
import com.stefanini.cycle_authenticate.application.ports.outbound.security.EncryptionServicePort;
import com.stefanini.cycle_authenticate.application.ports.outbound.security.SessionTokenService;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.exceptions.EmailNotWithinStandards;
import com.stefanini.cycle_authenticate.domain.exceptions.PasswordNotWithinStandards;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import com.stefanini.cycle_authenticate.domain.value_objects.Password;

public class UserServiceImpl implements UserServicePort {

    private UserRepositoryPort userRepositoryPort;

    private EncryptionServicePort encryptionServicePort;

    private SessionTokenService sessionTokenService;

    public UserServiceImpl(UserRepositoryPort userRepositoryPort, EncryptionServicePort encryptionServicePort, SessionTokenService sessionTokenService) {
        this.userRepositoryPort = userRepositoryPort;
        this.encryptionServicePort = encryptionServicePort;
        this.sessionTokenService = sessionTokenService;
    }

    @Override
    public User create(CreateUserDTO createUserDTO) throws EmailNotWithinStandards, PasswordNotWithinStandards, UserAlreadyExistsException {
        Email email = new Email(createUserDTO.getEmail());

        if (Password.isValid(createUserDTO.getPassword())) {
            throw new PasswordNotWithinStandards();
        }

        Password password = new Password(createUserDTO.getPassword());

        this.userRepositoryPort.findByEmail(email).orElseThrow(UserAlreadyExistsException::new);

        User user = new User(createUserDTO.getUsername(), email, password, createUserDTO.getDateOfBrith());

        return this.userRepositoryPort.save(user).orElseThrow(UserAlreadyExistsException::new);
    }

    @Override
    public SessionTokenDTO authenticate(Email email, Password password) throws UserNotFoundException {
        User user = this.userRepositoryPort.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Boolean passwordMatch = this.encryptionServicePort.match(user.getPassword().getValue(), password.getValue());

        if(!passwordMatch){
            throw new InputInvalidException("email/password invalid");
        }

        return this.sessionTokenService.generator(user.getId());
    }
}
