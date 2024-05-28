package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.AfterCinemaEra;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
@Builder
@EqualsAndHashCode(exclude = {"id", "description"})
@ToString
public class Film {
    private int id;
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotNull(message = "description is mandatory")
    @Length(max = 200, message = "description must be less than 200 digits")
    private String description;
    @AfterCinemaEra(message = "release date is too early")
    private LocalDate releaseDate;
    @Positive(message = "duration must be positive integer")
    private int duration;
    private final Set<Integer> likedUsers = ConcurrentHashMap.newKeySet();
    private final AtomicInteger likes = new AtomicInteger(0);

    public int getLikes() {
        return likes.get();
    }

    public boolean addLikeFromUser(Integer userId) {
        if (!likedUsers.contains(userId)) {
            likes.incrementAndGet();
            return true;
        }
        return false;
    }

    public boolean removeLikeFromUser(Integer userId) {
        if (likedUsers.contains(userId)) {
            likes.decrementAndGet();
            return true;
        }
        return false;
    }
}
