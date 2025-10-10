package com.stefanini.cycle_authenticate.application.services;

import com.stefanini.cycle_authenticate.application.exceptions.InputInvalidException;
import com.stefanini.cycle_authenticate.application.exceptions.UserAlreadyExistsException;
import com.stefanini.cycle_authenticate.application.exceptions.UserNotFoundException;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateUserBodyDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.SessionTokenDTO;
import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.UserRepositoryPort;
import com.stefanini.cycle_authenticate.application.ports.outbound.security.EncryptionServicePort;
import com.stefanini.cycle_authenticate.application.ports.outbound.security.SessionTokenServicePort;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.exceptions.EmailNotWithinStandards;
import com.stefanini.cycle_authenticate.domain.exceptions.PasswordNotWithinStandards;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import com.stefanini.cycle_authenticate.domain.value_objects.Password;
import com.stefanini.cycle_authenticate.domain.value_objects.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

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

    @Nested
    @DisplayName("success tests")
    class SuccessTests {

        @Test
        @DisplayName("should given an create new user")
        public void should_given_an_create_new_user() {
            CreateUserBodyDTO createUserDTO = new CreateUserBodyDTO("jhon_doe", "jhondoe@example.com", "JhonDoe2006@2025", LocalDate.now().minusYears(18), UserRole.USER.toString());
            Mockito.when(userRepositoryPort.findByEmail(Mockito.any())).thenReturn(Optional.empty());

            User userSaved = new User(UUID.randomUUID(), createUserDTO.username(), new Email(createUserDTO.email()), new Password(createUserDTO.password()), createUserDTO.dateOfBirth());
            Mockito.when(userRepositoryPort.save(Mockito.any())).thenReturn(Optional.of(userSaved));

            User user = userService.create(createUserDTO);
            Assertions.assertNotNull(user.getId());
            Assertions.assertEquals(user.getEmail(), userSaved.getEmail());
            Assertions.assertEquals(user.getPassword(), userSaved.getPassword());
            Assertions.assertEquals(user.getUsername(), userSaved.getUsername());
            Assertions.assertEquals(user.getDateOfBrith(), userSaved.getDateOfBrith());
        }

        @Test
        @DisplayName("should be given authenticate user")
        public void should_be_given_authenticate_user() throws UserNotFoundException {
            Email email = new Email("jhon_doe@example.com");
            Password password = new Password("JhonDoe2006@2025");
            User user = new User(UUID.randomUUID(), "jhon_doe", email, password, LocalDate.now().minusYears(18));

            Mockito.when(userRepositoryPort.findByEmail(Mockito.eq(email))).thenReturn(Optional.of(user));
            Mockito.when(encryptionServicePort.match(password.getValue(), user.getPassword().getValue())).thenReturn(true);

            String token = UUID.randomUUID().toString();
            Mockito.when(sessionTokenService.generator(user.getId())).thenReturn(new SessionTokenDTO(token, Instant.now().plus(Duration.ofMinutes(10)).toEpochMilli()));

            SessionTokenDTO sessionTokenDTO = userService.authenticate(email, password);
            Assertions.assertNotNull(sessionTokenDTO.getToken());
            Assertions.assertNotNull(sessionTokenDTO.getExpireAt());

//            10 minutes token
            long expireAtInMinutes = Duration.ofMillis(sessionTokenDTO.getExpireAt() - Instant.now().toEpochMilli()).toMinutes();
            org.assertj.core.api.Assertions.assertThat(expireAtInMinutes)
                    .isGreaterThanOrEqualTo(9)
                    .isLessThanOrEqualTo(10);

        }
    }

    @Nested
    @DisplayName("failed tests")
    class FailedTests {

        @Test
        @DisplayName("should give an error with an invalid email address")
        public void should_give_an_error_with_an_invalid_email_address() {
            CreateUserBodyDTO createUserDTO = new CreateUserBodyDTO("jhon_doe", "jhon", "JhonDoe2006@2025", LocalDate.now().minusYears(18), UserRole.USER.toString());
            Assertions.assertThrows(EmailNotWithinStandards.class, () -> {
                userService.create(createUserDTO);
            });
        }

        @Test
        @DisplayName("should give an error with a password that doesn't meet the standards")
        public void should_give_an_error_with_a_password_that_does_meet_the_standards() {
            CreateUserBodyDTO createUserDTO = new CreateUserBodyDTO("jhon_doe", "jhondoe@example.com", "123456", LocalDate.now().minusYears(18), UserRole.USER.toString());
            Assertions.assertThrows(PasswordNotWithinStandards.class, () -> {
                userService.create(createUserDTO);
            });
        }

        @Test
        @DisplayName("should give an error if a user with this email address already exists")
        public void should_give_an_error_if_a_user_with_this_email_address_already_exists() {
            CreateUserBodyDTO createUserDTO = new CreateUserBodyDTO("jhon_doe", "jhondoe@example.com", "JhonDoe2006@2025", LocalDate.now().minusYears(18), UserRole.USER.toString());
            Mockito.when(userRepositoryPort.findByEmail(Mockito.any())).thenReturn(Optional.of(new User()));

            Assertions.assertThrows(UserAlreadyExistsException.class, () -> {
                userService.create(createUserDTO);
            });
        }

        @Test
        @DisplayName("should given not authenticate user not found")
        public void should_given_not_authenticate_user_not_found() throws UserNotFoundException {
            Email email = new Email("jhon_doe@example.com");
            Password password = new Password("JhonDoe2006@2025");

            Mockito.when(userRepositoryPort.findByEmail(Mockito.eq(email))).thenReturn(Optional.empty());
            Assertions.assertThrows(UserNotFoundException.class,()->{
                userService.authenticate(email, password);
            });
        }

        @Test
        @DisplayName("should given not authenticate password not match")
        public void should_given_not_authenticate_password_not_match() {
            Email email = new Email("jhon_doe@example.com");
            Password password = new Password("JhonDoe2006@2025");
            User user = new User(UUID.randomUUID(), "jhon_doe", email, password, LocalDate.now().minusYears(18));

            Mockito.when(userRepositoryPort.findByEmail(Mockito.eq(email))).thenReturn(Optional.of(user));
            Mockito.when(encryptionServicePort.match(password.getValue(), user.getPassword().getValue())).thenReturn(false);

            Assertions.assertThrows(InputInvalidException.class,()->{
                userService.authenticate(email, password);
            });
        }
    }

}