package ru.yandex.practicum.filmorate.utils;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class UserMapper {
    public static UserDTO UserToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .login(user.getLogin())
                .name(user.getName())
                .birthday(user.getBirthday())
                .build();
    }
}
