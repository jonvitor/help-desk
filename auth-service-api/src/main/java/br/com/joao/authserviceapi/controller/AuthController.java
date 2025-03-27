package br.com.joao.authserviceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api/auth")
public interface AuthController {

    @Operation(summary = "Authenticate user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "User authenticated",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthenticateResponse.class))),
            @ApiResponse(
                    responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(
                    responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
    })
    @PostMapping("/login")
    ResponseEntity<AuthenticateResponse> authenticate(@Valid @RequestBody final AuthenticateRequest authenticateRequest);
}
