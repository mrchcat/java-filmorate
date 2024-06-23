package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequestDTO;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequestDTO;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("GET/users")
    void getAllTest() throws Exception {
        mockMvc.perform(get("/users"));
        Mockito.verify(userService).getAllUsers();
    }

    @Test
    @DisplayName("POST/users")
    void addUserTest() throws Exception {
        NewUserRequestDTO dto = NewUserRequestDTO.builder()
                .email("sss@mailru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1980, 1, 1))
                .build();
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/users").contentType("application/json").content(json));
        Mockito.verify(userService).addUser(dto);
    }

    @Test
    @DisplayName("PUT/users")
    void updateUserTest() throws Exception {
        UpdateUserRequestDTO dto = UpdateUserRequestDTO.builder()
                .id(2)
                .email("sss@mailru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1980, 1, 1))
                .build();
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/users").contentType("application/json").content(json));
        Mockito.verify(userService).updateUser(dto);
    }
}