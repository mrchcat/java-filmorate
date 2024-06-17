package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void getAll_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

//    @Test
//    void getAll_shouldReturnUsers() throws Exception {
//        List<User> users = List.of(User.builder().name("user1").build(), User.builder().name("user2").build());
//        when(userService.getAllUsers()).thenReturn(users);
//
//        mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(users)));
//    }


    @ParameterizedTest
    @MethodSource("provideInvalidUsers")
    void create_shouldResponseWithBadRequest_ifUserIsInvalid(User user) throws Exception {
        String json = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/users").contentType("application/json").content(json))
                .andExpect(status().isBadRequest());
        mockMvc.perform(put("/users").contentType("application/json").content(json))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> provideInvalidUsers() {
        return Stream.of(
                Arguments.of(user(u -> u.setLogin(" dd  dfd"))),
                Arguments.of(user(u -> u.setEmail("sssss"))),
                Arguments.of(user(u -> u.setBirthday(LocalDate.of(3333, 1, 1))))
        );
    }

    private static User user() {
        return User.builder()
                .email("sss@sss.ru")
                .login("ssss")
                .name("film")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
    }

    private static User user(Consumer<User> consumer) {
        User user = user();
        consumer.accept(user);
        return user;
    }

}