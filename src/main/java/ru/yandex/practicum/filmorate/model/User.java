package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
public class User {
    private Integer id;
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

