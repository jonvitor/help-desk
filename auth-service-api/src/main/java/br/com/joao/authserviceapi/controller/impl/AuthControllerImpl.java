package br.com.joao.authserviceapi.controller.impl;

import br.com.joao.authserviceapi.controller.AuthController;
import br.com.joao.authserviceapi.security.JWTAuthenticationImpl;
import br.com.joao.authserviceapi.service.RefreshTokenService;
import br.com.joao.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticateRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticateResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtil;

    private final AuthenticationConfiguration configuration;

    private final RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest authenticateRequest) throws Exception {
        return ResponseEntity.ok().body(
                new JWTAuthenticationImpl(jwtUtil, configuration.getAuthenticationManager())
                        .authenticate(authenticateRequest)
                        .withRefreshToken(refreshTokenService.save(authenticateRequest.email()).getId())
        );
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(request.refreshToken()));
    }
}
