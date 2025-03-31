package br.com.joao.authserviceapi.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import models.exceptions.StandardError;
import models.exceptions.ValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<StandardError> handleBadCredentialsException(final BadCredentialsException e,
                                                      final HttpServletRequest request) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                StandardError.builder()
                        .timeStamp(now())
                        .status(UNAUTHORIZED.value())
                        .error(UNAUTHORIZED.getReasonPhrase())
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                    .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationError> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e,
                                                                  final HttpServletRequest request) {
        var error = ValidationError.builder()
                .timeStamp(now())
                .status(BAD_REQUEST.value())
                .error(BAD_REQUEST.getReasonPhrase())
                .message("Validation error")
                .path(request.getRequestURI())
                .errors(new ArrayList<>())
                .build();

        e.getBindingResult().getFieldErrors().forEach(fieldError ->
               error.addError(fieldError.getField(), fieldError.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(error);
    }

}
