package br.com.joao.userserviceapi.service;

import br.com.joao.userserviceapi.mapper.UserMapper;
import br.com.joao.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.responses.UserReponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserReponse findById(final String id) {
        return userMapper.fromEntity(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }
}