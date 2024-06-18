package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequestDTO;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import ru.yandex.practicum.filmorate.utils.UserMapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("add user")
    void addUserTest() {
        NewUserRequestDTO dto = NewUserRequestDTO.builder()
                .email("sss@mailru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1980, 2, 1))
                .build();
        User user1 = User.builder()
                .email("sss@mailru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1980, 2, 1))
                .build();
        User user2 = User.builder()
                .id(1)
                .email("sss@mailru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1980, 2, 1))
                .build();
        Mockito.when(userRepository.addUser(user1)).thenReturn(user2);
        userService.addUser(dto);
        Mockito.verify(userRepository).addUser(user1);
        assertEquals(UserMapper.userToDTO(user2), userService.addUser(dto));
    }
}