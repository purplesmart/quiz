package com.ebay.quiz.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class Question {

    @Id
    public UUID Id;
    public String questionText;
    public String[] answers;
    public int rightAnswer;
    private final int MAX_POSSIBLE_ANSWERS = 4;

    public UUID getId() {
        return Id;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public Question() {
        CreateQuestion();
    }

    public Question(UUID id, String questionText, String[] answers, int rightAnswer) {
        this.Id = id;
        this.questionText = questionText;
        this.answers = new String[answers.length];
        System.arraycopy(answers, 0, this.answers, 0, answers.length);
        this.rightAnswer = -1;
    }

    public Question GetLightQuestion() {
        return new Question(Id, questionText, answers, -1);
    }

    private void CreateQuestion() {
        Id = UUID.randomUUID();
        questionText = String.format("This is the question for %s", Id.toString());
        answers = new String[MAX_POSSIBLE_ANSWERS];
        rightAnswer = ThreadLocalRandom.current().nextInt(0, MAX_POSSIBLE_ANSWERS - 1);
        for (int i = 0; i < MAX_POSSIBLE_ANSWERS; i++) {
            if (rightAnswer == i) answers[i] = Id.toString();
            else answers[i] = UUID.randomUUID().toString();
        }
    }
}
