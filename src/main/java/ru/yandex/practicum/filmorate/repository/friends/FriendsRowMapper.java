package ru.yandex.practicum.filmorate.repository.friends;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.yandex.practicum.filmorate.model.FriendshipStatus.CONFIRMED;
import static ru.yandex.practicum.filmorate.model.FriendshipStatus.REQUESTED;
import static ru.yandex.practicum.filmorate.model.FriendshipStatus.UNKNOWN;

@Component
public class FriendsRowMapper implements RowMapper<Friends> {

    @Override
    public Friends mapRow(ResultSet rs, int rowNum) throws SQLException {
        FriendshipStatus status=UNKNOWN;
        int status_id= rs.getInt("status_id");
        if(status_id== REQUESTED.getDatabaseId()){
            status=REQUESTED;
        } else if (status_id==CONFIRMED.getDatabaseId()) {
            status=CONFIRMED;
        }
        return Friends.builder()
                .applicantId(rs.getInt("user_id"))
                .approvingId(rs.getInt("friend_id"))
                .status(status)
                .build();
    }
}
