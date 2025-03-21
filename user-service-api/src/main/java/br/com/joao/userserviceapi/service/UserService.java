package br.com.joao.userserviceapi.service;

import br.com.joao.userserviceapi.entity.User;
import br.com.joao.userserviceapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(final String id) {
        return userRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("User not found");
        });
    }
}
