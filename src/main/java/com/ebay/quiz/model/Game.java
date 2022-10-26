package com.ebay.quiz.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Game {

    public Game(UUID id) {
        this.id = id;
        users = new ArrayList<>();
        questions = new ArrayList<>();
    }

    @Id
    public final UUID id;
    public final List<User> users;
    public final List<Question> questions;

    public UUID GetId() {
        return id;
    }

    public List<User> GetUsers() {
        return users;
    }

    public List<Question> GetQuestions() {
        return questions;
    }

    public Game() {
        id = UUID.randomUUID();
        users = new ArrayList<>();
        questions = new ArrayList<>();
    }
}
