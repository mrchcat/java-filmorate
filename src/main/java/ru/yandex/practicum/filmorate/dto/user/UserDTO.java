package ru.yandex.practicum.filmorate.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
public class UserDTO {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
