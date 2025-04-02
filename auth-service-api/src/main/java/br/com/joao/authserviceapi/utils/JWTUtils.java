package br.com.joao.authserviceapi.utils;

import br.com.joao.authserviceapi.security.dtos.UserDetailsDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(final UserDetailsDTO detailsDTO) {

        return Jwts.builder()
                .claim("id", detailsDTO.getId())
                .claim("authorities", detailsDTO.getAuthorities())
                .setSubject(detailsDTO.getUsername())
                .signWith(Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8)))
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                .compact();
    }
}
