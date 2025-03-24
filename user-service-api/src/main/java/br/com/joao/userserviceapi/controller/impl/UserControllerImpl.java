package br.com.joao.userserviceapi.controller.impl;

import br.com.joao.userserviceapi.controller.UserController;
import br.com.joao.userserviceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import models.responses.UserReponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserReponse> findById(String id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
