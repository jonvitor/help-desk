package br.com.joao.authserviceapi.controller.impl;

import br.com.joao.authserviceapi.controller.AuthController;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest authenticateRequest) {
        return ResponseEntity.ok().body(AuthenticateResponse.builder()
                        .type("Bearer")
                        .token("token")
                .build());
    }
}
