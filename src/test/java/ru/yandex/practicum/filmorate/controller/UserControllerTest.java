package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerTest {

    static UserController userController;

    @BeforeEach
    void initUserController() {
        userController = new UserController();
    }

    @Test
    @DisplayName("User controller: add correct user")
    void testUserControllerAddCorrectUser() {
        LocalDate birthday = LocalDate.of(2000, 1, 1);
        User user = User.builder().id(22).email("sss@sss.dd").login("login").name("name").birthday(birthday).build();
        ResponseEntity<User> response = userController.addUser(user);
        User answer = response.getBody();
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertEquals(1, requireNonNull(answer).getId());
        assertEquals(user.getEmail(), answer.getEmail());
        assertEquals(user.getLogin(), answer.getLogin());
        assertEquals(user.getName(), answer.getName());
        assertEquals(user.getBirthday(), answer.getBirthday());
    }

    @Test
    @DisplayName("User controller: update user")
    void testUserControllerUpdateUser() {
        LocalDate birthday = LocalDate.of(2000, 1, 1);
        User user = User.builder().id(22).email("sss@sss.dd").login("login").name("name").birthday(birthday).build();
        User user2 = User.builder()
                .id(1).email("sdd@dsd.fs").login("upLogin").name("upName").birthday(birthday).build();
        userController.addUser(user);
        ResponseEntity<User> response = userController.updateUser(user2);
        User answer = response.getBody();
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(1, requireNonNull(answer).getId());
        assertEquals(user2.getEmail(), answer.getEmail());
        assertEquals(user2.getLogin(), answer.getLogin());
        assertEquals(user2.getName(), answer.getName());
        assertEquals(1, requireNonNull(userController.getAllUsers()).size());
    }

    @Test
    @DisplayName("User controller: get all users")
    void testUserControllerGetAllUsers() {
        LocalDate birthday = LocalDate.of(2000, 1, 1);
        User user1 = User.builder().email("sss1@sss.dd").login("login1").name("name1").birthday(birthday).build();
        User user2 = User.builder().email("sss2@sss.dd").login("login2").name("name2").birthday(birthday).build();
        User user3 = User.builder().email("sss3@sss.dd").login("login3").name("name3").birthday(birthday).build();

        userController.addUser(user1);
        userController.addUser(user2);
        userController.addUser(user3);
        Collection<User> users = userController.getAllUsers();
        assertEquals(3, requireNonNull(users).size());
        assertTrue(users.stream().anyMatch(u -> u.getName().equals(user1.getName())));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals(user2.getName())));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals(user3.getName())));
        assertTrue(users.stream().anyMatch(u -> u.getId() == 1));
        assertTrue(users.stream().anyMatch(u -> u.getId() == 2));
        assertTrue(users.stream().anyMatch(u -> u.getId() == 3));
    }


    @Test
    @DisplayName("User controller: add user with bad date")
    void testUserControllerGetIdAndSaveUserBadDateTest() {
        LocalDate birthday = LocalDate.of(3000, 1, 1);
        User user = User.builder().id(22).email("sss@sss.dd").login("login").name("name").birthday(birthday).build();
        ResponseEntity<User> response = userController.addUser(user);
        User answer = response.getBody();
        assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
        assertEquals(user.getEmail(), requireNonNull(answer).getEmail());
        assertEquals(user.getLogin(), answer.getLogin());
        assertEquals(user.getName(), answer.getName());
        assertEquals(user.getBirthday(), answer.getBirthday());
    }

    @Test
    @DisplayName("User controller: add user with bad email")
    void testUserControllerGetIdAndSaveUserBadEmail() {
        LocalDate birthday = LocalDate.of(1965, 1, 1);
        User user = User.builder().id(22).email("ssssss.dd").login("login").name("name").birthday(birthday).build();
        ResponseEntity<User> response = userController.addUser(user);
        User answer = response.getBody();
        assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
        assertEquals(user.getEmail(), requireNonNull(answer).getEmail());
        assertEquals(user.getLogin(), answer.getLogin());
        assertEquals(user.getName(), answer.getName());
        assertEquals(user.getBirthday(), answer.getBirthday());
    }


    @Test
    @DisplayName("User controller: update user with bad id")
    void testUserControllerUpdateUserBadId() {
        LocalDate birthday = LocalDate.of(2000, 1, 1);
        User user = User.builder().id(22).email("sss@sss.dd").login("login").name("name").birthday(birthday).build();
        User user2 = User.builder()
                .id(100).email("sdd@dsd.fs").login("upLogin").name("upName").birthday(birthday).build();
        userController.addUser(user);
        ResponseEntity<User> response = userController.updateUser(user2);
        User answer = response.getBody();
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertEquals(user2.getEmail(), requireNonNull(answer).getEmail());
        assertEquals(user2.getLogin(), answer.getLogin());
        assertEquals(user2.getName(), answer.getName());
        assertEquals(1, userController.getAllUsers().size());
        assertTrue(userController.getAllUsers().stream().anyMatch(u -> u.getName().equals(user.getName())));
    }
}