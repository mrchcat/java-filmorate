package ru.yandex.practicum.filmorate.utils;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.film.FilmDTO;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

@Component
public class FilmMapper {
    public static FilmDTO FilmToDTO(Film film) {
        Rating rating=Rating.builder().id(film.getMpaId()).build();
        List<Genre> genres =film
                .getGenresId()
                .stream()
                .map(u->Genre.builder().id(u).build()).toList();

        return FilmDTO.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .mpa(rating)
                .genres(genres)
                .build();
    }

    public static Film newFilmRequestToFilm(NewFilmRequestDTO dto){
        List<Integer> genresId=dto
                .getGenres()
                .stream()
                .map(Genre::getId)
                .toList();

        return Film.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .releaseDate(dto.getReleaseDate())
                .duration(dto.getDuration())
                .mpaId(dto.getMpa().getId())
                .genresId(genresId)
                .build();
    }

    public static Film updateFilmRequestDTOToFilm(UpdateFilmRequestDTO dto){
        List<Integer> genresId=dto
                .getGenres()
                .stream()
                .map(Genre::getId)
                .toList();
        return Film.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .releaseDate(dto.getReleaseDate())
                .duration(dto.getDuration())
                .mpaId(dto.getMpa().getId())
                .genresId(genresId)
                .build();
    }
}
