package br.com.joao.userserviceapi.service;

import br.com.joao.userserviceapi.entity.User;
import br.com.joao.userserviceapi.mapper.UserMapper;
import br.com.joao.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(find(id));
    }

    private User find(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Object not found with id: " + id + " and type: " + UserResponse.class.getSimpleName()
        ));
    }

    public void save(CreateUserRequest request) {
        verifyIfEmailExists(request.email(), null);
        userRepository.save(userMapper.toEntity(request));
    }

    public void verifyIfEmailExists(final String email, final String id) {
        userRepository.findByEmail(email).ifPresent(user -> {
            if (!user.getId().equals(id)) {
                throw new DataIntegrityViolationException("Email " + email + " already exists");
            }
        });
    }

    public List<UserResponse> findAll() {
        return userMapper.fromEntities(userRepository.findAll());
    }

    public UserResponse update(String id, UpdateUserRequest request) {
        var user = find(id);
        verifyIfEmailExists(request.email(), id);
        return userMapper.fromEntity(userRepository.save(userMapper.copyProperties(request, user)));
    }
}