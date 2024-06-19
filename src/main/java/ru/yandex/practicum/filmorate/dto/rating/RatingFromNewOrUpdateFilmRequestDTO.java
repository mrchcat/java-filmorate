package ru.yandex.practicum.filmorate.dto.rating;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class RatingFromNewOrUpdateFilmRequestDTO {
    private int id;
}
