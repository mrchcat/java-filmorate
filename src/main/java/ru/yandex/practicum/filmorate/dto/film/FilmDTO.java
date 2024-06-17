package ru.yandex.practicum.filmorate.dto.film;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class FilmDTO {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Rating mpa;
    private List<Genre> genres;
}
