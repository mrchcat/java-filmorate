package ru.yandex.practicum.filmorate.dto.genre;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class GenreDTO {
    private int id;
    private String name;
}
