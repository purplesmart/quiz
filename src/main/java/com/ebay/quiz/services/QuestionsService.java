package com.ebay.quiz.services;

import com.ebay.quiz.model.*;

public interface QuestionsService {

     Question GetQuestion(Game game, User user);
     AnswerResult GetAnswerResult(Question question, Answer answer);

}
