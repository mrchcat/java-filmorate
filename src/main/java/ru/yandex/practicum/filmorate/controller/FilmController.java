package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.IdNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ObjectAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int count = 0;

    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        if (notUnique(film)) {
            throw new ObjectAlreadyExistsException("Film already exists", film);
        }
        int id = getNextId();
        film.setId(id);
        films.put(id, film);
        log.info("Film added: {}", film);
        return new ResponseEntity<>(film, HttpStatusCode.valueOf(201));
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        int id = film.getId();
        if (!films.containsKey(id)) {
            throw new IdNotFoundException(String.format("Film with id=%d is not found", film.getId()));
        }
        films.put(id, film);
        log.info("Film is updated: {}", film);
        return new ResponseEntity<>(film, HttpStatusCode.valueOf(200));
    }

    private boolean notUnique(Film film) {
        return films.containsValue(film);
    }

    private int getNextId() {
        return ++count;
    }
}
