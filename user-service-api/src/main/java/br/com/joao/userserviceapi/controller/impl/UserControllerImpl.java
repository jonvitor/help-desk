package br.com.joao.userserviceapi.controller.impl;

import br.com.joao.userserviceapi.controller.UserController;
import br.com.joao.userserviceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> findById(final String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Override
    public ResponseEntity<Void> save(final CreateUserRequest request) {
        userService.save(request);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Override
    public ResponseEntity<UserResponse> update(String id, UpdateUserRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }
}
