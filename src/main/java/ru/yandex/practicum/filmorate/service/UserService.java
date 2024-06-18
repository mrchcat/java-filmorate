package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequestDTO;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequestDTO;
import ru.yandex.practicum.filmorate.dto.user.UserDTO;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.friends.FriendsRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import ru.yandex.practicum.filmorate.utils.UserMapper;

import java.util.Collection;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;

    public UserDTO addUser(NewUserRequestDTO dto) {
        User user = UserMapper.newUserRequestDTOToUser(dto);
        setName(user);
        User newUser = userRepository.addUser(user);
        log.info("User added: {}", newUser);
        return UserMapper.userToDTO(newUser);
    }

    public UserDTO updateUser(UpdateUserRequestDTO dto) {
        User user = UserMapper.updateUserRequestDTOToUser(dto);
        throwIfUserNotPresent(user);
        setName(user);
        userRepository.updateUser(user);
        log.info("User updated: {}", user);
        return UserMapper.userToDTO(user);
    }

    public Collection<UserDTO> getAllUsers() {
        return userRepository
                .getAllUsers()
                .stream()
                .map(UserMapper::userToDTO)
                .toList();
    }

    public void sendRequestForFriendship(Integer applicantId, Integer approvingId) {
        throwIfUserNotPresent(applicantId);
        throwIfUserNotPresent(approvingId);
        throwIfTheSameUsers(applicantId, approvingId);
        friendsRepository.sendRequestForFriendship(applicantId, approvingId);
    }


    public void recallRequestForFriendship(Integer applicantId, Integer approvingId) {
        throwIfUserNotPresent(applicantId);
        throwIfUserNotPresent(approvingId);
        throwIfTheSameUsers(applicantId, approvingId);
        friendsRepository.recallRequestForFriendship(applicantId, approvingId);
    }

    public Collection<UserDTO> getAllFriends(Integer userId) {
        throwIfUserNotPresent(userId);
        return userRepository
                .getAllFriends(userId)
                .stream()
                .map(UserMapper::userToDTO)
                .toList();
    }

    public Collection<UserDTO> getMutualFriends(Integer userId, Integer otherId) {
        throwIfUserNotPresent(userId);
        throwIfUserNotPresent(otherId);
        throwIfTheSameUsers(userId, otherId);
        return userRepository
                .getMutualFriends(userId, otherId)
                .stream()
                .map(UserMapper::userToDTO)
                .toList();
    }

    public void throwIfUserNotPresent(User user) {
        Integer userId = user.getId();
        if (isNull(userId)) {
            throw new IdNotFoundException("User ID is empty. Can not update user");
        } else if (!userRepository.containsUserById(userId)) {
            throw new IdNotFoundException(String.format("User with id=%d was not found", userId));
        }
    }

    public void throwIfUserNotPresent(int userId) {
        if (!userRepository.containsUserById(userId)) {
            throw new IdNotFoundException(String.format("User with id=%d was not found", userId));
        }
    }

    public void throwIfTheSameUsers(int offerId, int acceptId) {
        if (offerId == acceptId) {
            throw new ObjectAlreadyExistsException("User can't be it's own friend", offerId);
        }
    }

    private void setName(User user) {
        String name = user.getName();
        if (isNull(name) || name.isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
