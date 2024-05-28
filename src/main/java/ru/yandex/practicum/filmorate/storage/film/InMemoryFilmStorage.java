package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Primary
public class InMemoryFilmStorage implements FilmStorage{
    private final Map<Integer, Film> films = new ConcurrentHashMap<>();
    private final AtomicInteger count=new AtomicInteger(0);


    @Override
    public boolean addUserLikeToFilm(Integer filmId, Integer userId) {
        return films.get(filmId).addLikeFromUser(userId);
    }

    @Override
    public boolean deleteUserLikeFromFilm(Integer filmId, Integer userId) {
        return films.get(filmId).removeLikeFromUser(userId);
    }

    @Override
    public Collection<Film> getMostPopularFilms(int count) {
        return films.values().stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .limit(count)
                .toList();
    }

    @Override
    public int getFilmLikes(Integer filmId) {
        return films.get(filmId).getLikes();
    }

    @Override
    public Film addFilm(Film film) {
        int id = getNextId();
        film.setId(id);
        films.put(id,film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(),film);
        return film;
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @Override
    public boolean containsFilm(Film film) {
        return films.containsValue(film);
    }

    @Override
    public boolean containsId(int id) {
        return films.containsKey(id);
    }

    private int getNextId() {
        return count.incrementAndGet();
    }
}
