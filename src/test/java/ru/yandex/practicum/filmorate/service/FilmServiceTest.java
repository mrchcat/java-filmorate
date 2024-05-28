package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {
    @Mock FilmStorage filmStorage;
    @Mock UserService userService;
    FilmService filmService;

    @BeforeEach
    void initService(){
        filmService=new FilmService(filmStorage, userService);
    }

    @Test
    @DisplayName("get likes from bad film ")
    void testGetLikesFromBadFilm(){
        Mockito.when(filmStorage.containsId(10)).thenReturn(false);
        assertThrows(IdNotFoundException.class,()->filmService.getFilmLikes(10));
    }

    @Test
    @DisplayName("add likes to film by bad user")
    void testAddLikeByBadUser(){
        Mockito.when(filmStorage.containsId(10)).thenReturn(true);
        Mockito.doThrow(new IdNotFoundException("")).when(userService).throwIfUserNotPresent(20);
        assertThrows(IdNotFoundException.class,()->filmService.addLikeToFilm(10,20));
    }


    @Test
    @DisplayName("verify that call to add like from storage")
    void testAddLike(){
        int filmId=10;
        int userId=20;
        Mockito.when(filmStorage.containsId(filmId)).thenReturn(true);
        Mockito.doNothing().when(userService).throwIfUserNotPresent(userId);
        filmService.addLikeToFilm(filmId, userId);
        Mockito.verify(filmStorage).addUserLikeToFilm(filmId, userId);
    }

    @Test
    @DisplayName("verify that call to get popular films")
    void testGetPopularFIlms(){
        int count =4;
        filmService.getMostPopularFilms(count);
        Mockito.verify(filmStorage).getMostPopularFilms(count);
    }


}