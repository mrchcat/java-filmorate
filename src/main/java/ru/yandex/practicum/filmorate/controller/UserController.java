package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Getter
    private final HashMap<Integer, User> users = new HashMap<>();
    private int count = 0;

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        if (user == null) {
            log.info("User is not added because is absent");
            throw new ValidationException("User is absent");
        }
        checkUserAttributes(user);
        if (notUnique(user)) {
            log.info("User is not added: {} because User already exists", user);
            throw new ValidationException("User already exists");
        }
        getIdAndSaveUser(user);
        log.info("User added: {}", user);
        return new ResponseEntity<>(user, HttpStatusCode.valueOf(201));
    }


    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        if (user == null) {
            log.info("User is not updated because User is absent");
            throw new NullPointerException("User is absent");
        }
        checkUserAttributes(user);
        int id = user.getId();
        if (users.containsKey(id)) {
            users.put(id, user);
            log.info("User updated: {}", user);
            return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
        } else {
            log.info("User is not updated: {} because Not found", user);
            return new ResponseEntity<>(user, HttpStatusCode.valueOf(404));
        }
    }

    private void getIdAndSaveUser(User user) {
        int id = getNextId();
        user.setId(id);
        users.put(id, user);
    }

    private boolean notUnique(User user) {
        return users.containsValue(user);
    }

    private void checkUserAttributes(User user) {
        String name = user.getName();
        if (isNull(name) || name.isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    private int getNextId() {
        return ++count;
    }

}
