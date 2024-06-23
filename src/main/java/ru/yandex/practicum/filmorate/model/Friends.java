package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Friends {
    private Integer applicantId;
    private Integer approvingId;
    private FriendshipStatus status;
}
