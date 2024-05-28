package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryFilmStorageTest {
    private InMemoryFilmStorage inMemoryFilmStorage;

    @BeforeEach
    void initStorage(){
        inMemoryFilmStorage=new InMemoryFilmStorage();
    }

    @Test
    @DisplayName("add and get film")
    void testAddAndGetFilms(){
        Film film1=Film.builder().name("film1").build();
        Film film2=Film.builder().name("film2").build();
        Film film3=Film.builder().name("film3").build();
        inMemoryFilmStorage.addFilm(film1);
        inMemoryFilmStorage.addFilm(film2);
        inMemoryFilmStorage.addFilm(film3);
        assertEquals(3, inMemoryFilmStorage.getAllFilms().size());
        assertTrue(inMemoryFilmStorage.containsFilm(film1));
        assertTrue(inMemoryFilmStorage.containsFilm(film2));
        assertTrue(inMemoryFilmStorage.containsFilm(film3));
        List<Film> films=inMemoryFilmStorage.getAllFilms().stream().toList();
        assertEquals("film"+films.get(0).getId(),films.get(0).getName());
        assertEquals("film"+films.get(1).getId(),films.get(1).getName());
        assertEquals("film"+films.get(2).getId(),films.get(2).getName());
    }

    @Test
    @DisplayName("add like")
    void testAddAndDeleteLikes(){
        Film film=Film.builder().name("film1").build();
        film=inMemoryFilmStorage.addFilm(film);
        int filmId=film.getId();
        int userId1=1;
        inMemoryFilmStorage.addUserLikeToFilm(filmId,userId1);
        int userId2=2;
        inMemoryFilmStorage.addUserLikeToFilm(filmId,userId2);
        assertEquals(2, inMemoryFilmStorage.getFilmLikes(filmId));
        inMemoryFilmStorage.addUserLikeToFilm(filmId,userId1);
        assertEquals(2, inMemoryFilmStorage.getFilmLikes(filmId));
        inMemoryFilmStorage.deleteUserLikeFromFilm(filmId,222);
        assertEquals(2, inMemoryFilmStorage.getFilmLikes(filmId));
        inMemoryFilmStorage.deleteUserLikeFromFilm(filmId,userId1);
        inMemoryFilmStorage.deleteUserLikeFromFilm(filmId,userId2);
        assertEquals(0, inMemoryFilmStorage.getFilmLikes(filmId));
    }

}