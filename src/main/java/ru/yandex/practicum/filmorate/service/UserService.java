package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User addUser(User user) {
        setName(user);
        if (userStorage.containsUser(user)) {
            throw new ObjectAlreadyExistsException("User already exists", user);
        }
        User newUser = userStorage.addUser(user);
        log.info("User added: {}", newUser);
        return newUser;
    }

    public User updateUser(User user) {
        setName(user);
        throwIfUserNotPresent(user.getId());
        userStorage.updateUser(user);
        log.info("User updated: {}", user);
        return user;
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public void addFriend(Integer offerId, Integer acceptId) {
        throwIfUserNotPresent(offerId);
        throwIfUserNotPresent(acceptId);
        throwIfTheSameUsers(offerId, acceptId);
        userStorage.addFriend(offerId, acceptId);
        log.info("User={} started friendship with user={}", offerId, acceptId);
    }

    public void deleteFriend(Integer offerId, Integer toDeleteId) {
        throwIfUserNotPresent(offerId);
        throwIfUserNotPresent(toDeleteId);
        throwIfTheSameUsers(offerId, toDeleteId);
        userStorage.deleteFriend(offerId, toDeleteId);
        log.info("User={} stopped friendship with user={}", offerId, toDeleteId);
    }

    public Collection<User> getAllFriends(Integer userId) {
        throwIfUserNotPresent(userId);
        return userStorage.getAllFriends(userId);
    }

    public Collection<User> getMutualFriends(Integer id, Integer otherId) {
        throwIfUserNotPresent(id);
        throwIfUserNotPresent(otherId);
        return userStorage.getMutualFriends(id, otherId);
    }

    public void throwIfUserNotPresent(int id) {
        if (!userStorage.containsId(id)) {
            throw new IdNotFoundException(String.format("User with id=%d is not found", id));
        }
    }

    public void throwIfTheSameUsers(Integer offerId, Integer acceptId) {
        if (offerId.equals(acceptId)) {
            throw new ObjectAlreadyExistsException("User can't be own friend", offerId);
        }
    }

    private void setName(User user) {
        String name = user.getName();
        if (isNull(name) || name.isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}
