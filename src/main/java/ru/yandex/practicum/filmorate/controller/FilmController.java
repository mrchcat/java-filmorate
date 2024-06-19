package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.film.FilmDTO;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDTO addFilm(@Valid @RequestBody NewFilmRequestDTO film) {
        return filmService.addFilm(film);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public FilmDTO updateFilm(@Valid @RequestBody UpdateFilmRequestDTO film) {
        return filmService.updateFilm(film);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<FilmDTO> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmDTO getFilmsById(@PathVariable("id") Integer filmId) {
        return filmService.getFilmById(filmId);
    }


    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addLikeToFilm(@PathVariable("id") @NotNull @PositiveOrZero Integer filmId,
                              @PathVariable @NotNull @PositiveOrZero Integer userId) {
        filmService.addLikeToFilm(filmId, userId);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public Collection<FilmDTO> getMostPopularFilms(@RequestParam(required = false) @PositiveOrZero Integer count) {
        return filmService.getMostPopularFilms(count);
    }

    @GetMapping("/{id}/like")
    @ResponseStatus(HttpStatus.OK)
    public int getFilmLikes(@PathVariable @NotNull @PositiveOrZero Integer id) {
        return filmService.getFilmLikes(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLikeFromFilm(@PathVariable("id") @NotNull @PositiveOrZero Integer filmId,
                                   @PathVariable @NotNull @PositiveOrZero Integer userId) {
        filmService.deleteLikeFromFilm(filmId, userId);
    }

}
