package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.IdNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ObjectAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int count = 0;

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        setName(user);
        if (notUnique(user)) {
            throw new ObjectAlreadyExistsException("User already exists", user);
        }
        int id = getNextId();
        user.setId(id);
        users.put(id, user);
        log.info("User added: {}", user);
        return new ResponseEntity<>(user, HttpStatusCode.valueOf(201));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        setName(user);
        int id = user.getId();
        if (!users.containsKey(id)) {
            throw new IdNotFoundException(String.format("User with id=%d is not found", user.getId()));
        }
        users.put(id, user);
        log.info("User updated: {}", user);
        return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
    }

    private boolean notUnique(User user) {
        return users.containsValue(user);
    }

    private void setName(User user) {
        String name = user.getName();
        if (isNull(name) || name.isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    private int getNextId() {
        return ++count;
    }
}
