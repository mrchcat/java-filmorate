package ru.yandex.practicum.filmorate.dto.genre;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class GenreDTO {
    private int id;
    private String name;
}
