package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@EqualsAndHashCode(exclude = {"id", "description"})
@ToString
public class Film {
    private int id;
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotNull (message = "description is mandatory")
    @Length(max = 200, message = "description must be less than 200 digits")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "duration must be positive integer")
    private int duration;
}
