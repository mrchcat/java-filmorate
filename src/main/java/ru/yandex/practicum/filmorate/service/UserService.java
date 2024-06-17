package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.friends.FriendsRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import ru.yandex.practicum.filmorate.utils.UserMapper;

import java.util.Collection;

import static java.util.Objects.isNull;
import static ru.yandex.practicum.filmorate.model.FriendshipStatus.ABSENT;
import static ru.yandex.practicum.filmorate.model.FriendshipStatus.CONFIRMED;
import static ru.yandex.practicum.filmorate.model.FriendshipStatus.REQUESTED;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;

    public UserDTO addUser(User user) {
        setName(user);
        if (userRepository.containsUserByValue(user)) {
            throw new ObjectAlreadyExistsException("User already exists", user);
        }
        User newUser = userRepository.addUser(user);
        log.info("User added: {}", newUser);
        return UserMapper.UserToDTO(newUser);
    }

    public UserDTO updateUser(User user) {
        throwIfUserNotPresent(user);
        setName(user);
        userRepository.updateUser(user);
        log.info("User updated: {}", user);
        return UserMapper.UserToDTO(user);
    }

    public Collection<UserDTO> getAllUsers() {
        return userRepository
                .getAllUsers()
                .stream()
                .map(UserMapper::UserToDTO)
                .toList();
    }

    public void sendRequestForFriendship(Integer applicantId, Integer approvingId) {
        throwIfUserNotPresent(applicantId);
        throwIfUserNotPresent(approvingId);
        throwIfTheSameUsers(applicantId, approvingId);
        var applicantToApprovingStatus = friendsRepository.getFriendshipStatus(applicantId, approvingId);
        var approvingToApplicantStatus = friendsRepository.getFriendshipStatus(approvingId, applicantId);

        if (applicantToApprovingStatus.equals(ABSENT)) {
            if (approvingToApplicantStatus.equals(ABSENT)) {
                friendsRepository.addFriendshipStatus(applicantId, approvingId, REQUESTED);
                return;
            }
            if (approvingToApplicantStatus.equals(REQUESTED)) {
                friendsRepository.addFriendshipStatus(applicantId, approvingId, CONFIRMED);
                friendsRepository.setFriendshipStatus(approvingId, applicantId, CONFIRMED);
                return;
            }
        }
        log.info("Repeated request of user={} to user={} for friendship was declined by service",
                applicantId, approvingId);
        throw new ObjectAlreadyExistsException("Request for friendship already exists", approvingId);
    }


    public void recallRequestForFriendship(Integer applicantId, Integer approvingId) {
        throwIfUserNotPresent(applicantId);
        throwIfUserNotPresent(approvingId);
        throwIfTheSameUsers(applicantId, approvingId);
        var applicantToApprovingStatus = friendsRepository.getFriendshipStatus(applicantId, approvingId);
        var approvingToApplicantStatus = friendsRepository.getFriendshipStatus(approvingId, applicantId);
        if (!applicantToApprovingStatus.equals(ABSENT)) {
            friendsRepository.deleteFriendshipStatus(applicantId, approvingId);
            if (approvingToApplicantStatus.equals(CONFIRMED)) {
                friendsRepository.setFriendshipStatus(approvingId, applicantId, REQUESTED);
            }
            log.info("User={} stopped friendship with user={}", approvingId, applicantId);
        }
    }

    public Collection<UserDTO> getAllFriends(Integer userId) {
        throwIfUserNotPresent(userId);
        return userRepository
                .getAllFriends(userId)
                .stream()
                .map(UserMapper::UserToDTO)
                .toList();
    }

    public Collection<UserDTO> getMutualFriends(Integer userId, Integer otherId) {
        throwIfUserNotPresent(userId);
        throwIfUserNotPresent(otherId);
        throwIfTheSameUsers(userId, otherId);
        return userRepository
                .getMutualFriends(userId,otherId )
                .stream()
                .map(UserMapper::UserToDTO)
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
