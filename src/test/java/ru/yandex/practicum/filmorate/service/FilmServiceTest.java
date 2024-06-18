package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.utils.FilmMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    FilmRepository filmRepository;
    @Mock
    FilmMapper filmMapper;


    @InjectMocks
    FilmService filmService;

    @Test
    @DisplayName("get all films")
    void getAllFilmsTest() {
        Film film = Film.builder()
                .id(1)
                .name("ssss")
                .description("sssss")
                .duration(120)
                .releaseDate(LocalDate.of(1990, 2, 2))
                .mpaId(1)
                .genresId(Set.of(1))
                .build();
        List<Film> films = List.of(film);
        Mockito.when(filmRepository.getAllFilms()).thenReturn(films);
        Mockito.when(filmMapper.filmToDTO(film)).thenReturn(null);
        filmService.getAllFilms();
        Mockito.verify(filmRepository).getAllFilms();
    }


}