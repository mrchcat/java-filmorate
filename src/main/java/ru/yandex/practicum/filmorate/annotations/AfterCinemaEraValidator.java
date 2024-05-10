package ru.yandex.practicum.filmorate.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class AfterCinemaEraValidator implements ConstraintValidator<AfterCinemaEra, LocalDate> {
    private static final LocalDate EARLIER_RELEASE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(EARLIER_RELEASE);
    }

    @Override
    public void initialize(AfterCinemaEra constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
