package ru.yandex.practicum.filmorate.repository.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.BaseRepository;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;

import java.util.Collection;
import java.util.List;

@Repository
public class FilmDBRepository extends BaseRepository<Film> implements FilmRepository {
    private static final String INSERT_QUERY =
            "INSERT INTO film(name, description, release_date, duration, mpa_rating_id) VALUES (?,?,?,?,?) returning id";
    private static final String FIND_BY_PARAM_QUERY =
            "SELECT * FROM film WHERE name=? AND release_date=? AND duration=?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM film WHERE id=?";
    private static final String UPDATE_QUERY =
            "UPDATE film SET name=?, description=?, release_date=?, duration=?, mpa_rating_id=? WHERE id = ?";
    private static final String DELETE_GENRES_QUERY = "DELETE FROM film_genre WHERE film_id=?";
    private static final String INSERT_GENRES_QUERY = "INSERT INTO film_genre(film_id,genre_id) VALUES (?,?)";
    private static final String GET_ALL_FILMS_QUERY = "SELECT * FROM film";
    private final GenreRepository genreRepository;


    public FilmDBRepository(JdbcTemplate jdbc, RowMapper<Film> mapper, GenreRepository genreRepository) {
        super(jdbc, mapper);
        this.genreRepository = genreRepository;
    }

    @Override
    public Film addFilm(Film film) {
        int filmId = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpaId()
        );
        film.setId(filmId);
        film.getGenresId().forEach(genreId -> insertMultKeys(INSERT_GENRES_QUERY, filmId, genreId));
        return film;
    }

    @Override
    public boolean containsFilmByValue(Film film) {
        List<Film> sameFilmList = findMany(FIND_BY_PARAM_QUERY, film.getName(), film.getReleaseDate(), film.getDuration());
        return !sameFilmList.isEmpty();
    }

    @Override
    public Film updateFilm(Film film) {
        int filmId = film.getId();
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpaId(),
                filmId
        );
        delete(DELETE_GENRES_QUERY, filmId);
        film.getGenresId().forEach(genreId -> insertMultKeys(INSERT_GENRES_QUERY, filmId, genreId));
        return film;
    }

    @Override
    public boolean containsFilmById(int filmId) {
        return findOne(FIND_BY_ID_QUERY, filmId).isPresent();
    }

    @Override
    public Collection<Film> getAllFilms() {
        Collection<Film> films = findMany(GET_ALL_FILMS_QUERY);
        for (Film film : films) {
            int filmId = film.getId();
            List<Integer> genresId = genreRepository.getGenresByFilm(filmId);
            film.setGenresId(genresId);
        }
        return films;
    }
}
