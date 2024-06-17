package ru.yandex.practicum.filmorate.repository.friends;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.repository.BaseRepository;

import java.util.Optional;

import static ru.yandex.practicum.filmorate.model.FriendshipStatus.ABSENT;
import static ru.yandex.practicum.filmorate.model.FriendshipStatus.UNKNOWN;

@Repository
@Primary
@Slf4j
public class FriendsDBRepository extends BaseRepository<Friends> implements FriendsRepository {

    private static final String GET_FRIENDSHIP_STATUS_QUERY = "SELECT * FROM friends WHERE user_id=? AND friend_id=?;";
    private static final String SET_FRIENDSHIP_STATUS_QUERY =
            "UPDATE friends SET status_id=? WHERE user_id=? AND friend_id=?;";
    private static final String ADD_FRIENDSHIP_STATUS_QUERY =
            "INSERT INTO friends (user_id, friend_id, status_id) VALUES (?,?,?);";
    private static final String DELETE_FRIENDSHIP_QUERY = "DELETE FROM friends WHERE user_id=? AND friend_id=?;";

    public FriendsDBRepository(JdbcTemplate jdbc, RowMapper<Friends> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public FriendshipStatus getFriendshipStatus(Integer applicantId, Integer approvingId) {
        Optional<Friends> result = findOne(GET_FRIENDSHIP_STATUS_QUERY, applicantId, approvingId);
        if (result.isEmpty()) {
            return ABSENT;
        }
        if (result.get().getStatus().equals(UNKNOWN)) {
            throw new InternalServerException("Unknown status in database");
        }
        return result.get().getStatus();
    }

    @Override
    public void setFriendshipStatus(Integer applicantId, Integer approvingId, FriendshipStatus status) {
        if (status.equals(UNKNOWN) || status.equals(ABSENT)) {
            throw new InternalServerException("Incorrect status");
        }
        update(
                SET_FRIENDSHIP_STATUS_QUERY,
                status.getDatabaseId(),
                applicantId,
                approvingId
        );
    }

    @Override
    public void addFriendshipStatus(Integer applicantId, Integer approvingId, FriendshipStatus status) {
        insertMultKeys(
                ADD_FRIENDSHIP_STATUS_QUERY,
                applicantId,
                approvingId,
                status.getDatabaseId()
        );
    }

    @Override
    public void deleteFriendshipStatus(Integer applicantId, Integer approvingId) {
        delete(DELETE_FRIENDSHIP_QUERY,applicantId,approvingId);
    }
}
