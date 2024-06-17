package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;
import ru.yandex.practicum.filmorate.utils.GenreMapper;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public Collection<GenreDTO> getAllGenres(){
        return genreRepository
                .getAllGenres()
                .stream()
                .map(GenreMapper::GenreToDTO)
                .toList();
    }

    public GenreDTO getGenreById(int genreId){
        return genreRepository
                .getGenreById(genreId)
                .map(GenreMapper::GenreToDTO)
                .orElseThrow(()->new IdNotFoundException("Genre with id="+genreId+" is not found"));
    }
}
