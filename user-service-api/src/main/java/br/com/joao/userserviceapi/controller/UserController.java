package br.com.joao.userserviceapi.controller;

import models.responses.UserReponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/users")
public interface UserController {

    @GetMapping("/{id}")
    ResponseEntity<UserReponse> findById(@PathVariable final String id);
}
