package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Collection<Film> getAllFilms();

    boolean containsFilm(Film film);

    boolean containsId(int id);

    boolean addUserLikeToFilm(Integer filmId, Integer userId);

    boolean deleteUserLikeFromFilm(Integer filmId, Integer userId);

    Collection<Film> getMostPopularFilms(int count);

    int getFilmLikes(Integer filmId);
}
