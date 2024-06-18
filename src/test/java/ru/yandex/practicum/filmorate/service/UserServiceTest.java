package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistsException;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
//    @Mock
//    UserRepository userStorage;
//    @InjectMocks
//    UserService userService;
//
//    @Test
//    @DisplayName("add friend to the same user")
//    void testAddFriendToSame() {
//        int userId = 1;
//        Mockito.when(userStorage.containsUserById(userId)).thenReturn(true);
//        assertThrows(ObjectAlreadyExistsException.class, () -> userService.addFriend(userId, userId));
//    }
//
//    @Test
//    @DisplayName("add friend to different users")
//    void testAddFriendToDifferent() {
//        int userId1 = 1;
//        int userId2 = 2;
//        Mockito.when(userStorage.containsUserById(userId1)).thenReturn(true);
//        Mockito.when(userStorage.containsUserById(userId2)).thenReturn(true);
//        userService.addFriend(userId1, userId2);
//        Mockito.verify(userStorage).requestFriendship(userId1, userId2);
//    }

}