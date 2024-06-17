package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.AfterCinemaEra;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
public class Film {
    private int id;
    private String name;
    private String description;
    private List<Integer> genresId;
    private Integer mpaId;
    private LocalDate releaseDate;
    private int duration;
}
