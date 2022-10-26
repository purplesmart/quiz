package com.ebay.quiz.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {


    @Id
    public String userName;
    public List<Answer> userAnswers;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
        userAnswers = new ArrayList<>();
    }

    public List<Answer> getUserAnswers() {
        return userAnswers;
    }

    public String getUserName() {
        return userName;
    }
}
