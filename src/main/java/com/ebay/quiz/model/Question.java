package com.ebay.quiz.model;

import java.util.UUID;

public class Question {
    public UUID Id;
    public String questionText;
    public String[] answersText;

    public Question(UUID id, String questionText, String[] answersText){
        this.Id = id;
        this.questionText = questionText;
        this.answersText = answersText;
    }

}
