package ru.yandex.practicum.filmorate.dto.film;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.rating.RatingFromNewOrUpdateFilmRequestDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewFilmRequestDTOTest {
    static ValidatorFactory validatorFactory;
    static Validator validator;

    @BeforeEach
    void initFilmController() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @AfterEach
    void closes() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("Validation: add film with blank name")
    void testValidationBadName() {
        LocalDate releaseDate = LocalDate.of(2000, 1, 1);
        NewFilmRequestDTO dto = NewFilmRequestDTO.builder()
                .name("")
                .description("desc")
                .releaseDate(releaseDate)
                .duration(10)
                .mpa(RatingFromNewOrUpdateFilmRequestDTO.builder().id(1).build())
                .build();
        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Validation: add film with good name")
    void testValidationGoodName() {
        LocalDate releaseDate = LocalDate.of(2000, 1, 1);
        NewFilmRequestDTO dto = NewFilmRequestDTO.builder()
                .name("ddd")
                .description("desc")
                .releaseDate(releaseDate)
                .duration(10)
                .mpa(RatingFromNewOrUpdateFilmRequestDTO.builder().id(1).build())
                .build();
        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Validation: add film with bad duration")
    void testValidationBadDuration() {
        LocalDate releaseDate = LocalDate.of(2000, 1, 1);
        NewFilmRequestDTO dto = NewFilmRequestDTO.builder()
                .name("kkkk")
                .description("desc")
                .releaseDate(releaseDate)
                .duration(-10)
                .mpa(RatingFromNewOrUpdateFilmRequestDTO.builder().id(1).build())
                .build();
        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Validation: add film with bad release date")
    void testValidationBadReleaseDate() {
        LocalDate releaseDate = LocalDate.of(600, 1, 1);
        NewFilmRequestDTO dto = NewFilmRequestDTO.builder()
                .name("name")
                .description("desc")
                .releaseDate(releaseDate)
                .duration(10)
                .mpa(RatingFromNewOrUpdateFilmRequestDTO.builder().id(1).build())
                .build();
        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Validation: add film with good release date")
    void testValidationGoodReleaseDate() {
        LocalDate releaseDate = LocalDate.of(1980, 1, 1);
        NewFilmRequestDTO dto = NewFilmRequestDTO.builder()
                .name("name")
                .description("desc")
                .releaseDate(releaseDate)
                .duration(10)
                .mpa(RatingFromNewOrUpdateFilmRequestDTO.builder().id(1).build())
                .build();
        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Validation: add film with without mpa")
    void testValidationWithoutMpa() {
        LocalDate releaseDate = LocalDate.of(1980, 1, 1);
        NewFilmRequestDTO dto = NewFilmRequestDTO.builder()
                .name("name")
                .description("desc")
                .releaseDate(releaseDate)
                .duration(10)
                .build();
        assertFalse(validator.validate(dto).isEmpty());
    }
}