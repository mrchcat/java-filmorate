package ru.yandex.practicum.filmorate.dto.film;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.AfterCinemaEra;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class UpdateFilmRequestDTO {
        @Positive(message = "id must be positive")
        private Integer id;
        @NotBlank(message = "name is mandatory")
        private String name;
        @NotNull(message = "description is mandatory")
        @Length(max = 200, message = "description must be less than 200 digits")
        private String description;
        @AfterCinemaEra(message = "release date is too early")
        private LocalDate releaseDate;
        @Positive(message = "duration must be positive integer")
        private int duration;
        @NotNull(message = "mpa rating is mandatory")
        private Rating mpa;
        @NotNull(message = "genres are mandatory")
        private List<Genre> genres;
}
