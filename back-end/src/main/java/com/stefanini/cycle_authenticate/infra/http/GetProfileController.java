package com.stefanini.cycle_authenticate.infra.http;

import com.stefanini.cycle_authenticate.application.ports.inbound.services.UserServicePort;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.infra.http.presenters.GetProfilePresenter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@Tag(name = "profile")
public class GetProfileController {

    @Autowired
    private UserServicePort userServicePort;

    @GetMapping
    @Operation(
            summary = "get profile authenticate",
            description = "get username, email, date of birth , role  of user authenticate"
    )
    @ApiResponse(
            responseCode = "200",
            description = "dados do usuario",
            content = @Content(
                    schema = @Schema(
                            implementation = GetProfilePresenter.class
                    )
            )
    )
    public ResponseEntity<Object> me(
            @AuthenticationPrincipal String username
    ){
        User user = this.userServicePort.getProfile(username);

        return ResponseEntity.ok().body(GetProfilePresenter.toHttp(user));
    }
}
