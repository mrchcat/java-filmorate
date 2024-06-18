package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Builder
@ToString
public class Film {
    private int id;
    private String name;
    private String description;
    private Set<Integer> genresId;
    private Integer mpaId;
    private LocalDate releaseDate;
    private int duration;
}
