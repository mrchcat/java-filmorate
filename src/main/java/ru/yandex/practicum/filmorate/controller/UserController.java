package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequestDTO;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequestDTO;
import ru.yandex.practicum.filmorate.dto.user.UserDTO;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addUser(@Valid @RequestBody NewUserRequestDTO user) {
        return userService.addUser(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@Valid @RequestBody UpdateUserRequestDTO user) {
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void sendRequestForFriendship(@PathVariable @NotNull @PositiveOrZero Integer id,
                                         @PathVariable @NotNull @PositiveOrZero Integer friendId) {
        userService.sendRequestForFriendship(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void recallRequestForFriendship(@PathVariable @NotNull @PositiveOrZero Integer id,
                                           @PathVariable @NotNull @PositiveOrZero Integer friendId) {
        userService.recallRequestForFriendship(id, friendId);
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDTO> getAllFriends(@PathVariable @NotNull @PositiveOrZero Integer id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDTO> getMutualFriends(@PathVariable @NotNull @PositiveOrZero Integer id,
                                                @PathVariable @NotNull @PositiveOrZero Integer otherId) {
        return userService.getMutualFriends(id, otherId);
    }

}
