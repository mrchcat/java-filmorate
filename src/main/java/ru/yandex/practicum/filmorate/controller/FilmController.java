package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.film.FilmDTO;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDTO addFilm(@Valid @RequestBody NewFilmRequestDTO film) {
        log.info("в контроллер поступил dto{}", film.toString());
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
    public Collection<Film> getMostPopularFilms(@RequestParam(required = false) @PositiveOrZero Integer count) {
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
