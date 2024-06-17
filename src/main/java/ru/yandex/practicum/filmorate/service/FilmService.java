package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.film.FilmDTO;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;
import ru.yandex.practicum.filmorate.repository.mparating.RatingRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import ru.yandex.practicum.filmorate.utils.FilmMapper;
import ru.yandex.practicum.filmorate.utils.UserMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    @Value("${filmorate.filmservice.count}")
    private int countByDefault;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;

//    public int getFilmLikes(Integer filmId) {
//        throwIfFilmNotPresent(filmId);
//        return filmStorage.getFilmLikes(filmId);
//    }
//
//
//    public void addLikeToFilm(Integer filmId, Integer userId) {
//        throwIfFilmNotPresent(filmId);
////        userService.throwIfUserNotPresent(userId);
//        if (filmStorage.addUserLikeToFilm(filmId, userId)) {
//            log.info("Like added by user={} to film={}", userId, filmId);
//        }
//    }
//
//    public void deleteLikeFromFilm(Integer filmId, Integer userId) {
//        throwIfFilmNotPresent(filmId);
////        userService.throwIfUserNotPresent(userId);
//        if (filmStorage.deleteUserLikeFromFilm(filmId, userId)) {
//            log.info("Like removed by user={} from film={}", userId, filmId);
//        }
//    }
//
//    public Collection<Film> getMostPopularFilms(Integer count) {
//        if (isNull(count) || count <= 0) {
//            count = countByDefault;
//        }
//        return filmStorage.getMostPopularFilms(count);
//    }

    public Collection<FilmDTO> getAllFilms() {
        return filmRepository.getAllFilms().stream()
                .map(FilmMapper::FilmToDTO)
                .toList();
    }

    public FilmDTO addFilm(NewFilmRequestDTO dto) {
        throwIfMPARatingNotPresent(dto.getMpa().getId());
        throwIfGenresNotPresent(dto.getGenres());

        Film film = FilmMapper.newFilmRequestToFilm(dto);
        if (filmRepository.containsFilmByValue(film)) {
            throw new ObjectAlreadyExistsException("Film already exists", film);
        }
        Film newFilm = filmRepository.addFilm(film);
        log.info("Film added: {}", newFilm);
        return FilmMapper.FilmToDTO(film);
    }

    public FilmDTO updateFilm(UpdateFilmRequestDTO dto) {
        throwIfFilmNotPresent(dto.getId());
        throwIfMPARatingNotPresent(dto.getMpa().getId());
        throwIfGenresNotPresent(dto.getGenres());
        Film film = FilmMapper.updateFilmRequestDTOToFilm(dto);
        filmRepository.updateFilm(film);
        log.info("Film updated: {}", film);
        return FilmMapper.FilmToDTO(film);
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

    private void throwIfGenresNotPresent(List<Genre> genres) {
        boolean isAllGenreIdPresent = genres.stream()
                .map(Genre::getId)
                .map(genreRepository::getGenreById)
                .allMatch(Optional::isPresent);
        if (!isAllGenreIdPresent) {
            throw new IllegalArgumentException("genre id is not found");
        }
    }
}

