package ru.yandex.practicum.filmorate.dto.film;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.AfterCinemaEra;
import ru.yandex.practicum.filmorate.dto.genre.GenreFromNewOrUpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.rating.RatingFromNewOrUpdateFilmRequestDTO;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class NewFilmRequestDTO {
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
    private RatingFromNewOrUpdateFilmRequestDTO mpa;
    private List<GenreFromNewOrUpdateFilmRequestDTO> genres;
}
