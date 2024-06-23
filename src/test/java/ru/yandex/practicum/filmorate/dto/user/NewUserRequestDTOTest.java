package ru.yandex.practicum.filmorate.dto.user;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewUserRequestDTOTest {
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
    @DisplayName("Add user with bad date")
    void addNewUserBadDateTest() {
        LocalDate birthday = LocalDate.of(3000, 1, 1);
        NewUserRequestDTO dto = NewUserRequestDTO.builder()
                .email("sss@sss.dd")
                .login("login")
                .name("name")
                .birthday(birthday)
                .build();
        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Add user with bad email")
    void addNewUserBadEmailTest() {
        LocalDate birthday = LocalDate.of(1965, 1, 1);
        NewUserRequestDTO dto = NewUserRequestDTO.builder()
                .email("ssssssdd.ee")
                .login("login")
                .name("name")
                .birthday(birthday)
                .build();
        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Add correct user")
    void addNewUserCorrectTest() {
        LocalDate birthday = LocalDate.of(1965, 1, 1);
        NewUserRequestDTO dto = NewUserRequestDTO.builder()
                .email("sss@mailru")
                .login("login")
                .name("name")
                .birthday(birthday)
                .build();
        assertTrue(validator.validate(dto).isEmpty());
    }
}

