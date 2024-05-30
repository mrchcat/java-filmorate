package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
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
    @DisplayName("User controller: add user with bad date")
    void testUserControllerGetIdAndSaveUserBadDateTest() {
        LocalDate birthday = LocalDate.of(3000, 1, 1);
        User user = User.builder().id(22).email("sss@sss.dd").login("login").name("name").birthday(birthday).build();
        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    @DisplayName("User controller: add user with bad email")
    void testUserControllerGetIdAndSaveUserBadEmail() {
        LocalDate birthday = LocalDate.of(1965, 1, 1);
        User user = User.builder().id(22).email("ssssssdd.ee").login("login").name("name").birthday(birthday).build();
        assertFalse(validator.validate(user).isEmpty());
    }
}