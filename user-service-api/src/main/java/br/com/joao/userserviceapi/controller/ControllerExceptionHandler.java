package br.com.joao.userserviceapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import models.exceptions.ResourceNotFoundException;
import models.exceptions.StandardError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<StandardError> handleResourceNotFoundException(final ResourceNotFoundException e,
                                                      final HttpServletRequest request) {
        return ResponseEntity.status(NOT_FOUND).body(
                StandardError.builder()
                        .timeStamp(now())
                        .status(NOT_FOUND.value())
                        .error(NOT_FOUND.getReasonPhrase())
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                    .build()
        );
    }
}
