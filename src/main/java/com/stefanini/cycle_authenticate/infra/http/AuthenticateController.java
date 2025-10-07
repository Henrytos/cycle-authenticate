package com.stefanini.cycle_authenticate.infra.http;

import com.stefanini.cycle_authenticate.application.exceptions.UserNotFoundException;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.UserServicePort;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateUserDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.SessionTokenDTO;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import com.stefanini.cycle_authenticate.domain.value_objects.Password;
import com.stefanini.cycle_authenticate.infra.http.dtos.AuthenticationBodyDTO;
import com.stefanini.cycle_authenticate.infra.http.presenters.CreateUserPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthenticateController {

    private UserServicePort userServicePort;

    public AuthenticateController(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    @PostMapping
    public ResponseEntity<CreateUserPresenter> create(
            @RequestBody CreateUserDTO createUserDTO
    ) throws UserNotFoundException {
        User user = this.userServicePort.create(createUserDTO);
        return ResponseEntity.ok().body(CreateUserPresenter.toHttp(user));
    }

    @PostMapping("/auth")
    public ResponseEntity<SessionTokenDTO> auth(
            @RequestBody AuthenticationBodyDTO dto
    ) throws UserNotFoundException {
        SessionTokenDTO sessionTokenDTO  = this.userServicePort.authenticate(new Email(dto.email()), new Password(dto.password()));
        return ResponseEntity.ok().body(sessionTokenDTO);
    }

}
