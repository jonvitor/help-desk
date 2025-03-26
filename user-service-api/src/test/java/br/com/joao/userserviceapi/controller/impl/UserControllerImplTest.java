package br.com.joao.userserviceapi.controller.impl;

import br.com.joao.userserviceapi.entity.User;
import br.com.joao.userserviceapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.joao.userserviceapi.creator.CreatorUtils.generateMock;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenCallFindByIdThenReturnSuccess() throws Exception {
        final var entity = generateMock(User.class);
        final var user = userRepository.save(entity);

        mockMvc.perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.profiles").isArray());

        userRepository.deleteById(user.getId());
    }

    @Test
    void whenCallFindByIdThenReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/users/{id}", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Object not found with id: 1 and type: UserResponse"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.timeStamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value("/api/users/1"))
                .andExpect(jsonPath("$.error").value(NOT_FOUND.getReasonPhrase()));
    }

    @Test
    void whenCallFindAllThenReturnSuccess() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

}