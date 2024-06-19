package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.film.FilmDTO;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.genre.GenreFromNewOrUpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;
import ru.yandex.practicum.filmorate.repository.mparating.RatingRepository;
import ru.yandex.practicum.filmorate.utils.FilmMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    @Value("${filmorate.filmservice.count}")
    private int countByDefault;
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final FilmMapper filmMapper;

    public int getFilmLikes(Integer filmId) {
        throwIfFilmNotPresent(filmId);
        return filmRepository.getFilmLikes(filmId);
    }

    public void addLikeToFilm(Integer filmId, Integer userId) {
        throwIfFilmNotPresent(filmId);
        userService.throwIfUserNotPresent(userId);
        if (!filmRepository.containsUserLikeForFilm(filmId, userId)) {
            filmRepository.addUserLikeToFilm(filmId, userId);
            log.info("Like added by user={} to film={}", userId, filmId);
        }
    }

    public void deleteLikeFromFilm(Integer filmId, Integer userId) {
        throwIfFilmNotPresent(filmId);
        userService.throwIfUserNotPresent(userId);
        if (filmRepository.containsUserLikeForFilm(filmId, userId)) {
            filmRepository.deleteUserLikeFromFilm(filmId, userId);
            log.info("Like removed by user={} from film={}", userId, filmId);
        }
    }

    public Collection<FilmDTO> getMostPopularFilms(Integer count) {
        if (isNull(count) || count <= 0) {
            count = countByDefault;
        }
        return filmRepository.getMostPopularFilms(count)
                .stream()
                .map(filmMapper::filmToDTO)
                .toList();
    }

    public Collection<FilmDTO> getAllFilms() {
        return filmRepository.getAllFilms()
                .stream()
                .map(filmMapper::filmToDTO)
                .toList();
    }

    public FilmDTO getFilmById(int filmId) {
        throwIfFilmNotPresent(filmId);
        return filmMapper.filmToDTO(filmRepository.getFilmById(filmId));
    }

    public FilmDTO addFilm(NewFilmRequestDTO dto) {
        throwIfMPARatingNotPresent(dto.getMpaDTO().getId());
        throwIfGenresNotPresent(dto.getGenresDTOList());
        Film film = FilmMapper.newFilmRequestToFilm(dto);
        Film newFilm = filmRepository.addFilm(film);
        log.info("Film added: {}", newFilm);
        return filmMapper.filmToDTO(film);
    }

    public FilmDTO updateFilm(UpdateFilmRequestDTO dto) {
        throwIfFilmNotPresent(dto.getId());
        throwIfMPARatingNotPresent(dto.getMpaDTO().getId());
        throwIfGenresNotPresent(dto.getGenresDTOList());
        Film film = FilmMapper.updateFilmRequestDTOToFilm(dto);
        filmRepository.updateFilm(film);
        log.info("Film updated: {}", film);
        return filmMapper.filmToDTO(film);
    }

    private void throwIfFilmNotPresent(Integer filmId) {
        if (!filmRepository.containsFilmById(filmId)) {
            throw new IdNotFoundException(String.format("Film with id=%d is not found", filmId));
        }
    }

    private void throwIfMPARatingNotPresent(Integer mpaId) {
        if (ratingRepository.getRatingById(mpaId).isEmpty()) {
            throw new IllegalArgumentException("mpa rating with id=" + mpaId + " is not found");
        }
    }

    private void throwIfGenresNotPresent(List<GenreFromNewOrUpdateFilmRequestDTO> genresDTOList) {
        if (genresDTOList == null) {
            return;
        }
        boolean isAllGenreIdPresent = genresDTOList
                .stream()
                .map(GenreFromNewOrUpdateFilmRequestDTO::getId)
                .map(genreRepository::getGenreById)
                .allMatch(Optional::isPresent);
        if (!isAllGenreIdPresent) {
            throw new IllegalArgumentException("genre id is not found");
        }
    }
}

