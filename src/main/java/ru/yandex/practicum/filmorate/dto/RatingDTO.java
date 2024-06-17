package ru.yandex.practicum.filmorate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class RatingDTO {
    private int id;
    private String name;
}
