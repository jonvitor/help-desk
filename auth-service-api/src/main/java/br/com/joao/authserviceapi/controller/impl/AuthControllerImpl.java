package br.com.joao.authserviceapi.controller.impl;

import br.com.joao.authserviceapi.controller.AuthController;
import br.com.joao.authserviceapi.security.JWTAuthenticationImpl;
import br.com.joao.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtil;

    private final AuthenticationConfiguration configuration;

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest authenticateRequest) throws Exception {
        return ResponseEntity.ok().body(
                new JWTAuthenticationImpl(jwtUtil, configuration.getAuthenticationManager())
                        .authenticate(authenticateRequest)
        );
    }
}
