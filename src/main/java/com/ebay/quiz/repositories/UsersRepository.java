package com.ebay.quiz.repositories;

import com.ebay.quiz.model.User;
import java.util.Optional;

public interface UsersRepository  {
    Optional<User> findById(String userName);
    User save(User user);

}
