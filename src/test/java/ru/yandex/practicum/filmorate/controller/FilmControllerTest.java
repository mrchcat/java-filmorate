package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmControllerTest {

    static FilmController filmController;

    @BeforeEach
    void initFilmController() {
        filmController = new FilmController();
    }

    @Test
    @DisplayName("Film controller: add correct film")
    void testFilmControllerAddCorrectFilm() {
        LocalDate releaseDate = LocalDate.of(2000, 1, 1);
        Film film = Film.builder().id(22).name("name").description("desc").releaseDate(releaseDate).duration(100).build();
        ResponseEntity<Film> response = filmController.addFilmHandler(film);
        Film answer = response.getBody();
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertEquals(1, requireNonNull(answer).getId());
        assertEquals(film.getName(), answer.getName());
        assertEquals(film.getDescription(), answer.getDescription());
        assertEquals(film.getReleaseDate(), answer.getReleaseDate());
        assertEquals(film.getDuration(), answer.getDuration());
    }

    @Test
    @DisplayName("Film controller: update film")
    void testFilmControllerUpdateFilm() {
        LocalDate releaseDate = LocalDate.of(2000, 1, 1);
        Film film = Film.builder().id(22).name("name").description("desc").releaseDate(releaseDate).duration(100).build();
        LocalDate updatedRelease = LocalDate.of(2010, 1, 1);
        Film film2 = Film.builder().
                id(1).name("newName").description("newDesc").releaseDate(updatedRelease).duration(120).build();

        filmController.addFilmHandler(film);
        ResponseEntity<Film> response = filmController.updateFilmHandler(film2);
        Film answer = response.getBody();

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(1, requireNonNull(answer).getId());
        assertEquals(film2.getName(), answer.getName());
        assertEquals(film2.getDescription(), answer.getDescription());
        assertEquals(film2.getReleaseDate(), answer.getReleaseDate());
        assertEquals(film2.getDuration(), answer.getDuration());
        assertEquals(1, requireNonNull(filmController.getAllFilmsHandler().getBody()).size());
    }

    @Test
    @DisplayName("Film controller: get all films")
    void testFilmControllerGetAllFilms() {
        LocalDate releaseDate = LocalDate.of(2000, 1, 1);
        Film film1 = Film.builder().id(22).name("name").description("desc").releaseDate(releaseDate).duration(100).build();
        Film film2 = Film.builder().name("name2").description("desc2").releaseDate(releaseDate).duration(120).build();
        Film film3 = Film.builder().name("name3").description("desc3").releaseDate(releaseDate).duration(100).build();
        filmController.addFilmHandler(film1);
        filmController.addFilmHandler(film2);
        filmController.addFilmHandler(film3);
        ResponseEntity<Collection<Film>> response = filmController.getAllFilmsHandler();
        Collection<Film> films = response.getBody();
        assertEquals(3, requireNonNull(films).size());
        assertTrue(films.stream().anyMatch(u -> u.getName().equals(film1.getName())));
        assertTrue(films.stream().anyMatch(u -> u.getName().equals(film2.getName())));
        assertTrue(films.stream().anyMatch(u -> u.getName().equals(film3.getName())));
        assertTrue(films.stream().anyMatch(u -> u.getId() == 1));
        assertTrue(films.stream().anyMatch(u -> u.getId() == 2));
        assertTrue(films.stream().anyMatch(u -> u.getId() == 3));
    }

    @Test
    @DisplayName("Film controller: add film with bad date")
    void testFilmControllerAddFilmBadDateTest() {
        LocalDate releaseDate = LocalDate.of(1000, 1, 1);
        Film film = Film.builder().name("name").description("desc").releaseDate(releaseDate).duration(100).build();
        ResponseEntity<Film> response = filmController.addFilmHandler(film);
        Film answer = response.getBody();
        assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
        assertEquals(film.getName(), requireNonNull(answer).getName());
        assertEquals(film.getDescription(), answer.getDescription());
        assertEquals(film.getReleaseDate(), answer.getReleaseDate());
        assertEquals(film.getDuration(), answer.getDuration());
    }

    @Test
    @DisplayName("Film controller: add film with bad duration")
    void testFilmControllerAddFilmBadDuration() {
        LocalDate releaseDate = LocalDate.of(1000, 1, 1);
        Film film = Film.builder().name("sss").description("desc").releaseDate(releaseDate).duration(-10).build();
        ResponseEntity<Film> response = filmController.addFilmHandler(film);
        Film answer = response.getBody();
        assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
        assertEquals(film.getName(), requireNonNull(answer).getName());
        assertEquals(film.getDescription(), answer.getDescription());
        assertEquals(film.getReleaseDate(), answer.getReleaseDate());
        assertEquals(film.getDuration(), answer.getDuration());
    }

    @Test
    @DisplayName("Film controller: update film with bad id")
    void testFilmControllerUpdateFilmBadId() {
        LocalDate releaseDate = LocalDate.of(2000, 1, 1);
        Film film = Film.builder().id(22).name("name").description("desc").releaseDate(releaseDate).duration(100).build();
        LocalDate updatedRelease = LocalDate.of(2010, 1, 1);

        Film film2 = Film.builder().
                id(100).name("newName").description("newDesc").releaseDate(updatedRelease).duration(120).build();

        filmController.addFilmHandler(film);
        ResponseEntity<Film> response = filmController.updateFilmHandler(film2);
        Film answer = response.getBody();

        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertEquals(film2.getName(), requireNonNull(answer).getName());
        assertEquals(film2.getDescription(), answer.getDescription());
        assertEquals(film2.getReleaseDate(), answer.getReleaseDate());
        assertEquals(film2.getDuration(), answer.getDuration());
        assertTrue(requireNonNull(filmController.getAllFilmsHandler().getBody()).stream()
                .anyMatch(u -> u.getName().equals(film.getName())));
    }
}