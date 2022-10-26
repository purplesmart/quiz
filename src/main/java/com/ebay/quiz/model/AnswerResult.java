package com.ebay.quiz.model;

public class AnswerResult {

    public AnswerResult(AnswerStatus answerStatus, int pointsEarned) {
        this.answerStatus = answerStatus;
        this.pointsEarned = pointsEarned;
    }

    public AnswerStatus getAnswerStatus() {
        return answerStatus;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    AnswerStatus answerStatus;
    int pointsEarned;

}
