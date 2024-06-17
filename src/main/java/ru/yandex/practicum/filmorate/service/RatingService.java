package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.RatingDTO;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.repository.mparating.RatingRepository;
import ru.yandex.practicum.filmorate.utils.RatingMapper;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public Collection<RatingDTO> getAllRatings(){
        return ratingRepository
                .getAllRatings()
                .stream()
                .map(RatingMapper::RatingToDTO)
                .toList();
    }

    public RatingDTO getRatingById(int ratingId){
        return ratingRepository
                .getRatingById(ratingId)
                .map(RatingMapper::RatingToDTO)
                .orElseThrow(()->new IdNotFoundException("MPA rating with id="+ratingId+" is not found"));
    }

}
