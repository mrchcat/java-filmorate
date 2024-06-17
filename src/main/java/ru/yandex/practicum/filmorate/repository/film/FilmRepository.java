package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmRepository {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    boolean containsFilmByValue(Film film);

    boolean containsFilmById(int filmId);

    Collection<Film> getAllFilms();

//    boolean addUserLikeToFilm(Integer filmId, Integer userId);
//
//    boolean deleteUserLikeFromFilm(Integer filmId, Integer userId);
//
//    Collection<Film> getMostPopularFilms(int count);
//
//    int getFilmLikes(Integer filmId);
}
