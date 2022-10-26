package com.ebay.quiz.services;

import com.ebay.quiz.model.Answer;
import com.ebay.quiz.model.User;

public interface UsersService {

    User GetUser(String userName);
    void UserAnswer(User user, Answer answer);

}
