package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
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
    public ResponseEntity<Collection<User>> getAllUsersHandler() {
        return new ResponseEntity<>(users.values(), HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<User> addUserHandler(@RequestBody User user) {
        try {
            if (user == null) throw new NullPointerException("User is absent");
            checkUserAttrubutes(user);
            if (notUnique(user)) throw new ValidationException("User already exists");
            addUser(user);
            return new ResponseEntity<>(user, HttpStatusCode.valueOf(201));
        } catch (NullPointerException | ValidationException e) {
            log.info("User is not added: {} because {}", user, e.getMessage());
            return new ResponseEntity<>(user, HttpStatusCode.valueOf(400));
        }
    }


    @PutMapping
    public ResponseEntity<User> updateUserHandler(@RequestBody User user) {
        try {
            if (user == null) throw new NullPointerException("User is absent");
            checkUserAttrubutes(user);
            int id = user.getId();
            if (users.containsKey(id)) {
                users.put(id, user);
                log.info("User updated: {}", user);
                return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
            } else {
                log.info("User is not updated: not found {}", user);
                return new ResponseEntity<>(user, HttpStatusCode.valueOf(404));
            }
        } catch (NullPointerException | ValidationException e) {
            log.info("User is not updated: {} because {}", user, e.getMessage());
            return new ResponseEntity<>(user, HttpStatusCode.valueOf(400));
        }
    }

    private void addUser(User user) {
        int id = getNextId();
        user.setId(id);
        users.put(id, user);
        log.info("User added: {}", user);
    }

    private boolean notUnique(User user) {
        return users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()));
    }

    private void checkUserAttrubutes(User user) {
        String email = user.getEmail();
        if (isNull(email) || !email.matches("^\\S+@\\S+\\.\\S+$")) {
            throw new ValidationException("Error in e-mail");
        }

        String login = user.getLogin();
        if (isNull(login) || login.isEmpty() || login.matches("\\s")) {
            throw new ValidationException("Login can't be empty or contain spaces");
        }

        String name = user.getName();
        if (isNull(name) || name.isEmpty()) {
            user.setName(login);
        }

        LocalDate birthdate = user.getBirthday();
        if (isNull(birthdate) || birthdate.isAfter(LocalDate.now())) {
            throw new ValidationException("Error in birthday date");
        }
    }

    private int getNextId() {
        count++;
        return count;
    }

}
