package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    Collection<User> getAllUsers();

    boolean containsUser(User user);

    boolean containsId(int id);

    void addFriend(Integer offerId, Integer acceptId);

    void deleteFriend(Integer offerId, Integer toDeleteId);

    Collection<User> getAllFriends(Integer userId);

    Collection<User> getMutualFriends(Integer id, Integer otherId);

}