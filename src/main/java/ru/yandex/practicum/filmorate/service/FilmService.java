package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    @Value("${filmorate.filmservice.count}")
    private int countByDefault;
    private final FilmStorage filmStorage;
    private final UserService userService;

    public int getFilmLikes(Integer filmId) {
        throwIfFilmNotPresent(filmId);
        return filmStorage.getFilmLikes(filmId);
    }


    public void addLikeToFilm(Integer filmId, Integer userId) {
        throwIfFilmNotPresent(filmId);
        userService.throwIfUserNotPresent(userId);
        if (filmStorage.addUserLikeToFilm(filmId, userId)) {
            log.info("Like added by user={} to film={}", userId, filmId);
        }
    }

    public void deleteLikeFromFilm(Integer filmId, Integer userId) {
        throwIfFilmNotPresent(filmId);
        userService.throwIfUserNotPresent(userId);
        if (filmStorage.deleteUserLikeFromFilm(filmId, userId)) {
            log.info("Like removed by user={} from film={}", userId, filmId);
        }
    }

    public Collection<Film> getMostPopularFilms(Integer count) {
        if (isNull(count) || count <= 0) {
            count = countByDefault;
        }
        return filmStorage.getMostPopularFilms(count);
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film addFilm(Film film) {
        if (filmStorage.containsFilm(film)) {
            throw new ObjectAlreadyExistsException("Film already exists", film);
        }
        Film newFilm = filmStorage.addFilm(film);
        log.info("Film added: {}", newFilm);
        return newFilm;
    }

    public Film updateFilm(Film film) {
        throwIfFilmNotPresent(film.getId());
        filmStorage.updateFilm(film);
        log.info("Film is updated: {}", film);
        return film;
    }

    private void throwIfFilmNotPresent(Integer id) {
        if (!filmStorage.containsId(id)) {
            throw new IdNotFoundException(String.format("Film with id=%d is not found", id));
        }
    }
}

