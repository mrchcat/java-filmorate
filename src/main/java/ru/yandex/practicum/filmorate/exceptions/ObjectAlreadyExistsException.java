package ru.yandex.practicum.filmorate.exceptions;

import lombok.Getter;

@Getter
public class ObjectAlreadyExistsException extends RuntimeException {
    private final Object object;

    public ObjectAlreadyExistsException(String message, Object object) {
        super(message);
        this.object = object;
    }
}
