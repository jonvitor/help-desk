package br.com.joao.authserviceapi.security;

import br.com.joao.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.joao.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final JWTUtils jwtUtil;

    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse authenticate(final AuthenticateRequest request) {
        try {
            log.info("Authenticating user: {}", request.email());
            final var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            return buildResponse((UserDetailsDTO) authentication.getPrincipal());
        } catch (BadCredentialsException e) {
            log.error("Invalid credentials: {}", e.getMessage());
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    protected AuthenticateResponse buildResponse(final UserDetailsDTO detailsDTO) {
        log.info("Generating JWT token for user: {}", detailsDTO.getUsername());
        final var token = jwtUtil.generateToken(detailsDTO);
        return AuthenticateResponse.builder()
                .type("JWT")
                .token("Bearer " + token)
                .build();
    }
}
