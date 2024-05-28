package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(ex.getMessage(),null );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(IdNotFoundException.class)
    public  ErrorResponse handleIdNotFoundExceptions(IdNotFoundException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(ex.getMessage(),null );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @org.springframework.web.bind.annotation.ExceptionHandler(ObjectAlreadyExistsException.class)
    public  ErrorResponse handleObjectAlreadyExistsException(ObjectAlreadyExistsException ex) {
        log.info("{}: {}", ex.getMessage(), ex.getObject());
        return new ErrorResponse(ex.getMessage(),null );
    }

}
