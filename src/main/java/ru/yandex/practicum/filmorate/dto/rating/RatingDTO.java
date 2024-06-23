package ru.yandex.practicum.filmorate.dto.rating;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class RatingDTO {
    private int id;
    private String name;
}
