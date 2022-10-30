package com.ebay.quiz.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;

@Entity
public class QuestionEntity {

    @Id
    public UUID Id;
    public String questionText;
    public String[] answersText;
    private final int MAX_POSSIBLE_ANSWERS = 4;

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    private int rightAnswer;

    public void setAnswerStatus(AnswerStatus answerStatus) {
        this.answerStatus = answerStatus;
    }

    private AnswerStatus answerStatus;

    public AnswerStatus getAnswerStatus() {
        return answerStatus;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public UUID getId() {
        return Id;
    }

    public QuestionEntity() {
        CreateQuestion();
    }

    public QuestionEntity(UUID id, String questionText, String[] answersText) {
        this.Id = id;
        this.questionText = questionText;
        this.answersText = new String[answersText.length];
        System.arraycopy(answersText, 0, this.answersText, 0, answersText.length);
        answerStatus = AnswerStatus.Pending;
        rightAnswer = -1;
    }

    public Question GetLightQuestion() {
        return new Question(Id, questionText, answersText);
    }

    private void CreateQuestion() {
        Id = UUID.randomUUID();
        questionText = String.format("This is the question for %s", Id.toString());
        answersText = new String[MAX_POSSIBLE_ANSWERS];
        for (int i = 0; i < MAX_POSSIBLE_ANSWERS; i++) {
            answersText[i] = UUID.randomUUID().toString();
        }
        answerStatus = AnswerStatus.Pending;
        rightAnswer = -1;
    }
}
