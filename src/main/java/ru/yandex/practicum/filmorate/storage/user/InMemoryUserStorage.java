package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Primary
public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger count=new AtomicInteger(0);
    private final Map<Integer, Set<Integer>> friends = new ConcurrentHashMap<>();

    @Override
    public void addFriend(Integer offerId, Integer acceptId) {
        if(friends.containsKey(offerId)&&friends.get(offerId).contains(acceptId)){
            return;
        }
        addUserIdToFriendList(offerId,acceptId);
        addUserIdToFriendList(acceptId,offerId);
    }

    private void addUserIdToFriendList(Integer offerId, Integer acceptId){
        Set<Integer> userSet;
        if(!friends.containsKey(offerId)) {
            userSet = ConcurrentHashMap.newKeySet();
            friends.put(offerId, userSet);
        }
        friends.get(offerId).add(acceptId);
    }

    @Override
    public void deleteFriend(Integer offerId, Integer toDeleteId) {
        if(!friends.containsKey(offerId)||!friends.get(offerId).contains(toDeleteId)){
            return;
        }
        deleteUserIdFromFriendList(offerId,toDeleteId);
        deleteUserIdFromFriendList(toDeleteId,offerId);
    }

    private void deleteUserIdFromFriendList(Integer offerId, Integer toDeleteId) {
        Set<Integer> userSet=friends.get(offerId);
        userSet.remove(toDeleteId);
        if(userSet.isEmpty()){
            friends.remove(offerId);
        }
    }

    @Override
    public Collection<User> getAllFriends(Integer userId) {
        if(friends.containsKey(userId)){
            return friends.get(userId).stream().map(users::get).toList();
        } else{
            return Collections.emptyList();
        }
    }

    @Override
    public Collection<User> getMutualFriends(Integer id, Integer otherId) {
        System.out.println("Внутри getMutualFriends");
        if(!friends.containsKey(id)||!friends.containsKey(otherId)){
            return Collections.emptyList();
        }
        Set<Integer> setUser=friends.get(id);
        Set<Integer> setOtherUser=friends.get(otherId);
        return setUser.stream()
                .filter(setOtherUser::contains)
                .map(users::get)
                .toList();
    }

    @Override
    public User addUser(User user) {
        int id = getNextId();
        user.setId(id);
        users.put(id,user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(),user);
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public boolean containsUser(User user) {
        return users.containsValue(user);
    }

    @Override
    public boolean containsId(int id) {
        return users.containsKey(id);
    }

    private int getNextId() {
        return count.incrementAndGet();
    }

}
