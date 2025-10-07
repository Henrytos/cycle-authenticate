package com.stefanini.cycle_authenticate.application.services;

import com.stefanini.cycle_authenticate.application.exceptions.UserAlreadyExistsException;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateUserDTO;
import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.UserRepositoryPort;
import com.stefanini.cycle_authenticate.application.ports.outbound.security.EncryptionServicePort;
import com.stefanini.cycle_authenticate.application.ports.outbound.security.SessionTokenServicePort;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.exceptions.EmailNotWithinStandards;
import com.stefanini.cycle_authenticate.domain.exceptions.PasswordNotWithinStandards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private EncryptionServicePort encryptionServicePort;

    @Mock
    private SessionTokenServicePort sessionTokenService;

    @Test
    @DisplayName("should give an error with an invalid email address")
    public void should_give_an_error_with_an_invalid_email_address() {
        CreateUserDTO createUserDTO = new CreateUserDTO("jhon_doe", "jhon", "JhonDoe2006@2025", LocalDate.now().minusYears(18));
        Assertions.assertThrows(EmailNotWithinStandards.class, () -> {
            this.userService.create(createUserDTO);
        });
    }

    @Test
    @DisplayName("should give an error with a password that doesn't meet the standards")
    public void should_give_an_error_with_a_password_that_does_meet_the_standards() {
        CreateUserDTO createUserDTO = new CreateUserDTO("jhon_doe", "jhondoe@example.com", "123456", LocalDate.now().minusYears(18));
        Assertions.assertThrows(PasswordNotWithinStandards.class, () -> {
            this.userService.create(createUserDTO);
        });
    }

    @Test
    @DisplayName("should give an error if a user with this email address already exists")
    public void should_give_an_error_if_a_user_with_this_email_address_already_exists() {
        CreateUserDTO createUserDTO = new CreateUserDTO("jhon_doe", "jhondoe@example.com", "JhonDoe2006@2025", LocalDate.now().minusYears(18));
        Mockito.when(this.userRepositoryPort.findByEmail(Mockito.any())).thenReturn(Optional.of(new User()));

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> {
            this.userService.create(createUserDTO);
        });
    }

}
