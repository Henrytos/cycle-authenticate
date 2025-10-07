package com.stefanini.cycle_authenticate.application.services;

import com.stefanini.cycle_authenticate.application.exceptions.InputInvalidException;
import com.stefanini.cycle_authenticate.application.exceptions.UserAlreadyExistsException;
import com.stefanini.cycle_authenticate.application.exceptions.UserNotFoundException;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.UserServicePort;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateUserDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.SessionTokenDTO;
import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.UserRepositoryPort;
import com.stefanini.cycle_authenticate.application.ports.outbound.security.EncryptionServicePort;
import com.stefanini.cycle_authenticate.application.ports.outbound.security.SessionTokenServicePort;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.exceptions.EmailNotWithinStandards;
import com.stefanini.cycle_authenticate.domain.exceptions.PasswordNotWithinStandards;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import com.stefanini.cycle_authenticate.domain.value_objects.Password;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserServicePort {

    private UserRepositoryPort userRepositoryPort;

    private EncryptionServicePort encryptionServicePort;

    private SessionTokenServicePort sessionTokenService;

    public UserServiceImpl(UserRepositoryPort userRepositoryPort, EncryptionServicePort encryptionServicePort, SessionTokenServicePort sessionTokenService) {
        this.userRepositoryPort = userRepositoryPort;
        this.encryptionServicePort = encryptionServicePort;
        this.sessionTokenService = sessionTokenService;
    }

    @Override
    public User create(CreateUserDTO createUserDTO) throws EmailNotWithinStandards, PasswordNotWithinStandards, UserAlreadyExistsException {
        if (!Email.isValid(createUserDTO.email())) {
            throw new EmailNotWithinStandards();
        }
        Email email = new Email(createUserDTO.email());

        if (!Password.isValid(createUserDTO.password())) {
            throw new PasswordNotWithinStandards();
        }
        Password password = new Password(this.encryptionServicePort.encode(createUserDTO.password()));
        Optional<User> userFind = this.userRepositoryPort.findByEmail(email);
        if (userFind.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        ;

        User user = new User(createUserDTO.username(), email, password, createUserDTO.dateOfBirth());

        return this.userRepositoryPort.save(user).orElseThrow(UserAlreadyExistsException::new);
    }

    @Override
    public SessionTokenDTO authenticate(Email email, Password password) throws UserNotFoundException {
        User user = this.userRepositoryPort.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Boolean passwordMatch = this.encryptionServicePort.match(password.getValue(), user.getPassword().getValue());

        if (!passwordMatch) {
            throw new InputInvalidException("email/password invalid");
        }

        return this.sessionTokenService.generator(user.getId());
    }
}
