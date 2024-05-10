package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final LocalDate EARLIER_RELEASE = LocalDate.of(1895, 12, 28);
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int count = 0;

    @GetMapping
    public ResponseEntity<Collection<Film>> getAllFilmsHandler() {
        return new ResponseEntity<>(films.values(), HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<Film> addFilmHandler(@RequestBody Film film) {
        try {
            if (film == null) throw new NullPointerException("Film is absent");
            checkFilmAttributes(film);
            if (notUnique(film)) throw new ValidationException("Film already exists");
            addFilm(film);
            return new ResponseEntity<>(film, HttpStatusCode.valueOf(201));
        } catch (NullPointerException | ValidationException e) {
            log.info("Film is not added: {} because {}", film, e.getMessage());
            return new ResponseEntity<>(film, HttpStatusCode.valueOf(400));
        }
    }

    @PutMapping
    public ResponseEntity<Film> updateFilmHandler(@RequestBody Film film) {
        try {
            if (film == null) throw new NullPointerException("Film is absent");
            int id = film.getId();
            if (films.containsKey(id)) {
                films.put(id, film);
                log.info("Film updated: {}", film);
                return new ResponseEntity<>(film, HttpStatusCode.valueOf(200));
            } else {
                log.info("Film is not updated: not found {}", film);
                return new ResponseEntity<>(film, HttpStatusCode.valueOf(404));
            }
        } catch (NullPointerException | ValidationException e) {
            log.info("Film is not updated: {} because {}", film, e.getMessage());
            return new ResponseEntity<>(film, HttpStatusCode.valueOf(400));
        }
    }

    private void addFilm(Film film) {
        int id = getNextId();
        film.setId(id);
        films.put(id, film);
        log.info("Film added: {}", film);
    }

    private boolean notUnique(Film film) {
        return films.values().stream().anyMatch(u ->
                u.getName().equals(film.getName()) &&
                        u.getReleaseDate().equals(film.getReleaseDate()) &&
                        u.getDuration().equals(film.getDuration()));

    }

    private void checkFilmAttributes(Film film) {
        String name = film.getName();
        if (isNull(name) || name.isEmpty()) {
            throw new ValidationException("Name can't be empty");
        }
        String description = film.getDescription();
        if (nonNull(description) && description.length() > 200) {
            throw new ValidationException("Description can not exceed 200 signs");
        }
        LocalDate release = film.getReleaseDate();
        if (isNull(release) || release.isBefore(EARLIER_RELEASE)) {
            throw new ValidationException("Release date can not be before " + EARLIER_RELEASE);
        }
        Integer duration = film.getDuration();
        if (isNull(duration) || duration <= 0)
            throw new ValidationException("Duration should be positive");
    }

    private int getNextId() {
        count++;
        return count;
    }

}
