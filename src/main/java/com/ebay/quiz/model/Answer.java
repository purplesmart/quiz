package com.ebay.quiz.model;

import java.util.UUID;

public class Answer {

    public final String userName;
    public final UUID gameId;
    public final UUID questionId;
    public int answerId;

    public Answer(String userName, UUID gameId, UUID questionId, int answerId) {
        this.userName = userName;
        this.gameId = gameId;
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public String getUserName() {
        return userName;
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

}
