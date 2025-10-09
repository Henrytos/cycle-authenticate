package com.stefanini.cycle_authenticate.infra.http;

import com.stefanini.cycle_authenticate.application.exceptions.UserNotFoundException;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.UserServicePort;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateUserBodyDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.SessionTokenDTO;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import com.stefanini.cycle_authenticate.domain.value_objects.Password;
import com.stefanini.cycle_authenticate.infra.configs.http.ResponseMessageDTO;
import com.stefanini.cycle_authenticate.infra.http.dtos.AuthenticationBodyDTO;
import com.stefanini.cycle_authenticate.infra.http.presenters.CreateUserPresenter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class AuthenticateController {

    private UserServicePort userServicePort;

    public AuthenticateController(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    @Tag(name = "user")
    @Operation(
            summary = "Criação de usuario",
            description = "rota de criação de nova conta de usuario"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            description = "successfully crated user",
                            content = @Content(schema = @Schema(implementation = CreateUserPresenter.class, contentMediaType = MediaType.APPLICATION_JSON_VALUE)),
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "user already exists",
                            content = @Content(schema = @Schema(implementation = ResponseMessageDTO.class, contentMediaType = MediaType.APPLICATION_JSON_VALUE)),
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description =
                                    """
                                    Requisição inválida (400) devido a diversos motivos, como:
                                    - Campo inválido, e-mail já cadastrado, ou erro de formato.
                                    - E-mail inválido.
                                    - Senha fora do padrão (caracter especial, maiúscula/minúscula, máx 20 caracteres).
                                    """,
                            content = @Content(schema = @Schema(implementation = ResponseMessageDTO.class, contentMediaType = MediaType.APPLICATION_JSON_VALUE)),
                            responseCode = "400"
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<CreateUserPresenter> create(
            @Valid @RequestBody CreateUserBodyDTO createUserDTO
    ) throws UserNotFoundException {
        User user = this.userServicePort.create(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateUserPresenter.toHttp(user));
    }

    @Tag(name = "auth")
    @Operation(
            summary = "Autenticação de usuario",
            description = "Rota de autenticação atravéz de email e senha "
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            description = "successfully authentication user",
                            content = @Content(schema = @Schema(implementation = SessionTokenDTO.class, contentMediaType = MediaType.APPLICATION_JSON_VALUE)),
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "user not found",
                            content = @Content(schema = @Schema(implementation = ResponseMessageDTO.class, contentMediaType = MediaType.APPLICATION_JSON_VALUE)),
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = """
                                    Motivos para este resultado:
                                    - Senha invalida
                                    - Requisição mal feita campos nulos ou vazios
                                    - E-mail invalido(formato)
                                    """,
                            content = @Content(schema = @Schema(implementation = ResponseMessageDTO.class, contentMediaType = MediaType.APPLICATION_JSON_VALUE)),
                            responseCode = "400"
                    ),
            }
    )
    @PostMapping("/auth")
    public ResponseEntity<SessionTokenDTO> auth(
            @Valid @RequestBody AuthenticationBodyDTO dto
    ) throws UserNotFoundException {
        SessionTokenDTO sessionTokenDTO = this.userServicePort.authenticate(new Email(dto.email()), new Password(dto.password()));
        return ResponseEntity.ok().body(sessionTokenDTO);
    }

}
