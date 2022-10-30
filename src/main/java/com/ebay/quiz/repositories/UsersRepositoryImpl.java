package com.ebay.quiz.repositories;

import com.ebay.quiz.model.User;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository{

    private Map<String, User> userMap;

    public UsersRepositoryImpl() {
        userMap = new HashMap<>();
    }

    @Override
    public User save(User user) {
        String userName = user.getUserName();
        userMap.put(userName, user);
        return userMap.get(userName);
    }

    @Override
    public Optional<User> findById(String userName) {
        if (userMap.containsKey(userName)) {
            return Optional.of(userMap.get(userName));
        } else {
            return Optional.empty();
        }
    }
}
