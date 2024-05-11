package ru.yandex.practicum.filmorate.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info(ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdNotFoundException.class)
    public  Map<String, String> handleIdNotFoundExceptions(IdNotFoundException ex) {
        log.info(ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        String fieldName = "error";
        String errorMessage = ex.getMessage();
        errors.put(fieldName, errorMessage);
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public  Map<String, String> handleObjectAlreadyExistsException(ObjectAlreadyExistsException ex) {
        log.info("{}: {}", ex.getMessage(), ex.getObject());
        Map<String, String> errors = new HashMap<>();
        String fieldName = "error";
        String errorMessage = ex.getMessage();
        errors.put(fieldName, errorMessage);
        return errors;
    }

}
