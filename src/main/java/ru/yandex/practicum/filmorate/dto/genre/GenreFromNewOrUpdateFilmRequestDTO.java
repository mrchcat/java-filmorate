package ru.yandex.practicum.filmorate.dto.genre;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class GenreFromNewOrUpdateFilmRequestDTO {
    @Positive(message = "genre id must be positive integer")
    private int id;
    private String name;
}
