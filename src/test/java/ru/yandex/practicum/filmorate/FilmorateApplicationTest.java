package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.BaseRepository;
import ru.yandex.practicum.filmorate.repository.genre.GenreDBRepository;
import ru.yandex.practicum.filmorate.repository.genre.GenreRowMapper;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes = {GenreDBRepository.class, GenreRowMapper.class, BaseRepository.class})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class FilmorateApplicationTest {
    private final GenreDBRepository genreDBRepository;

    @Test
    public void getGenresTest() {
        Collection<Genre> genres = genreDBRepository.getAllGenres();
        assertEquals(6, genres.size());
        assertTrue(true);
    }
}