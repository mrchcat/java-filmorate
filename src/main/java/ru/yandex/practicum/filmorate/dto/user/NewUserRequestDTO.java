package ru.yandex.practicum.filmorate.dto.user;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class NewUserRequestDTO {
    @NotNull(message = "email is mandatory")
    @Email(message = "email is not valid ")
    private String email;
    @NotBlank(message = "login is mandatory")
    @Pattern(regexp = "[^\\s]*", message = "login can't have spaces")
    private String login;
    private String name;
    @NotNull(message = "birthday is mandatory")
    @Past(message = "birthday can not be in the future")
    private LocalDate birthday;
}
