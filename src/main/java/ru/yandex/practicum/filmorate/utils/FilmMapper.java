package ru.yandex.practicum.filmorate.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.film.FilmDTO;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;
import ru.yandex.practicum.filmorate.repository.mparating.RatingRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FilmMapper {
    private final Map<Integer, Genre> allGenres = new HashMap<>();
    private final Map<Integer, Rating> allRatings = new HashMap<>();


    public FilmMapper(GenreRepository genreRepository, RatingRepository ratingRepository) {
        genreRepository.getAllGenres().forEach(genre -> allGenres.put(genre.getId(), genre));
        ratingRepository.getAllRatings().forEach(mpaRating -> allRatings.put(mpaRating.getId(), mpaRating));
    }

    public FilmDTO filmToDTO(Film film) {
        List<Genre> genres = film
                .getGenresId()
                .stream()
                .map(allGenres::get)
                .toList();

        return FilmDTO.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .mpa(allRatings.get(film.getMpaId()))
                .genres(genres)
                .build();
    }

    public static Film newFilmRequestToFilm(NewFilmRequestDTO dto) {
        List<Genre> genres = dto.getGenres();
        if (genres == null) {
            genres = Collections.emptyList();
        }
        Set<Integer> genresId = genres
                .stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());

        return Film.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .releaseDate(dto.getReleaseDate())
                .duration(dto.getDuration())
                .mpaId(dto.getMpa().getId())
                .genresId(genresId)
                .build();
    }

    public static Film updateFilmRequestDTOToFilm(UpdateFilmRequestDTO dto) {
        List<Genre> genres = dto.getGenres();
        if (genres == null) {
            genres = Collections.emptyList();
        }
        Set<Integer> genresId = genres
                .stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());

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
