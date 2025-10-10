package com.stefanini.cycle_authenticate.infra.http;

import com.stefanini.cycle_authenticate.application.ports.inbound.services.UserServicePort;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.infra.http.presenters.GetProfilePresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
public class GetProfileController {

    @Autowired
    private UserServicePort userServicePort;

    @GetMapping
    public ResponseEntity<Object> me(
            @AuthenticationPrincipal String username
    ){
        User user = this.userServicePort.getProfile(username);

        return ResponseEntity.ok().body(GetProfilePresenter.toHttp(user));
    }
}
