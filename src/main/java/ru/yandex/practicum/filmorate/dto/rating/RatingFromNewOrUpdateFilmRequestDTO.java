package ru.yandex.practicum.filmorate.dto.rating;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class RatingFromNewOrUpdateFilmRequestDTO {
    @Positive(message = "rating id must be positive integer")
    private int id;
    private String name;
}
