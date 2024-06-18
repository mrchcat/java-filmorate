package ru.yandex.practicum.filmorate.repository.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryUserRepositoryTest {
//    InMemoryUserStorage inMemoryUserStorage;
//
//    @BeforeEach
//    void initUserStorage() {
//        inMemoryUserStorage = new InMemoryUserStorage();
//    }
//
//    @Test
//    @DisplayName("add and update user")
//    void testAddAndDeleteUsers() {
//        User user1 = User.builder().name("user1").build();
//        User user2 = User.builder().name("user2").build();
//        user1 = inMemoryUserStorage.addUser(user1);
//        user2 = inMemoryUserStorage.addUser(user2);
//        User user3 = inMemoryUserStorage.addUser(User.builder().name("user3").build());
//        assertEquals(3, inMemoryUserStorage.getAllUsers().size());
//        assertEquals(1, user1.getId());
//        assertEquals(2, user2.getId());
//        assertEquals(3, user3.getId());
//        user3.setName("user4");
//        inMemoryUserStorage.updateUser(user3);
//        Collection<User> users = inMemoryUserStorage.getAllUsers().stream().toList();
//        String name = users.stream().filter(u -> u.equals(user3)).limit(1).toList().get(0).getName();
//        assertEquals("user4", name);
//    }
//
//    @Test
//    @DisplayName("add friends")
//    void testAddFriends() {
//        User user1 = inMemoryUserStorage.addUser(User.builder().name("user1").build());
//        User user2 = inMemoryUserStorage.addUser(User.builder().name("user2").build());
//        User user3 = inMemoryUserStorage.addUser(User.builder().name("user3").build());
//        User user4 = inMemoryUserStorage.addUser(User.builder().name("user4").build());
//        User user5 = inMemoryUserStorage.addUser(User.builder().name("user5").build());
//        inMemoryUserStorage.requestFriendship(user1.getId(), user2.getId());
//        inMemoryUserStorage.requestFriendship(user1.getId(), user2.getId());
//        assertEquals(1, inMemoryUserStorage.getAllFriends(user1.getId()).size());
//        inMemoryUserStorage.requestFriendship(user1.getId(), user3.getId());
//        assertEquals(2, inMemoryUserStorage.getAllFriends(user1.getId()).size());
//        assertEquals(1, inMemoryUserStorage.getAllFriends(user2.getId()).size());
//        assertEquals(1, inMemoryUserStorage.getAllFriends(user3.getId()).size());
//        assertEquals(0, inMemoryUserStorage.getAllFriends(user4.getId()).size());
//    }
//
//    @Test
//    @DisplayName("get mutual friends")
//    void testGetMutualFriends() {
//        User user1 = inMemoryUserStorage.addUser(User.builder().name("user1").build());
//        User user2 = inMemoryUserStorage.addUser(User.builder().name("user2").build());
//        User user3 = inMemoryUserStorage.addUser(User.builder().name("user3").build());
//        User user4 = inMemoryUserStorage.addUser(User.builder().name("user4").build());
//        User user5 = inMemoryUserStorage.addUser(User.builder().name("user5").build());
//        inMemoryUserStorage.requestFriendship(user1.getId(), user2.getId());
//        inMemoryUserStorage.requestFriendship(user1.getId(), user3.getId());
//        inMemoryUserStorage.requestFriendship(user4.getId(), user2.getId());
//        inMemoryUserStorage.requestFriendship(user4.getId(), user3.getId());
//        inMemoryUserStorage.requestFriendship(user4.getId(), user5.getId());
//        Collection<User> mutual = inMemoryUserStorage.getMutualFriends(user1.getId(), user4.getId());
//        assertEquals(2, mutual.size());
//        assertTrue(mutual.contains(user2));
//        assertTrue(mutual.contains(user3));
//    }
}