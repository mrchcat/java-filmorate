package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"id", "login", "name", "birthday"})
public class User {
    private int id;
    @NotNull
    @Email
    private String email;
    @NotBlank(message = "login is mandatory")
    @Pattern(regexp = "[^\\s]*")
    private String login;
    private String name;
    @NotNull
    @Past
    private LocalDate birthday;
}

