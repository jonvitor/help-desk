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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder encoder;

    public UserResponse findById(final String id) {
        return mapper.fromEntity(find(id));
    }

    private User find(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Object not found with id: " + id + " and type: " + UserResponse.class.getSimpleName()
        ));
    }

    public void save(CreateUserRequest request) {
        verifyIfEmailExists(request.email(), null);
        repository.save(
                mapper.toEntity(request).withPassword(encoder.encode(request.password()))
        );
    }

    public void verifyIfEmailExists(final String email, final String id) {
        repository.findByEmail(email).ifPresent(user -> {
            if (!user.getId().equals(id)) {
                throw new DataIntegrityViolationException("Email " + email + " already exists");
            }
        });
    }

    public List<UserResponse> findAll() {
        return mapper.fromEntities(repository.findAll());
    }

    public UserResponse update(String id, UpdateUserRequest request) {
        var user = find(id);
        verifyIfEmailExists(request.email(), id);
        return mapper.fromEntity(
                repository.save(
                    mapper.copyProperties(request, user))
                        .withPassword(request.password() != null ? encoder.encode(request.password()) : user.getPassword())
        );
    }
}