package br.com.joao.authserviceapi.service;

import br.com.joao.authserviceapi.models.RefreshToken;
import br.com.joao.authserviceapi.repositories.RefreshTokenRepository;
import br.com.joao.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.joao.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.exceptions.RefreshTokenExpiredException;
import models.exceptions.ResourceNotFoundException;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.expiration-sec.refresh-token}")
    private Long expirationSecRefreshToken;

    private final RefreshTokenRepository repository;

    private final JWTUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    public RefreshToken save(final String userName) {
        return repository.save(RefreshToken.builder()
                .id(UUID.randomUUID().toString())
                .userName(userName)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusSeconds(expirationSecRefreshToken))
                .build());
    }

    public RefreshTokenResponse refreshToken(final String refreshTokenId) {
        final var token = repository.findById(refreshTokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found. Id: " + refreshTokenId));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RefreshTokenExpiredException("Refresh token expired. Id: " + refreshTokenId);
        }

        return new RefreshTokenResponse(
                jwtUtils.generateToken((UserDetailsDTO) userDetailsService.loadUserByUsername(token.getUserName()))
        );
    }
}
