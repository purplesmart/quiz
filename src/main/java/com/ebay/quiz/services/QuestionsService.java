package com.ebay.quiz.services;

import com.ebay.quiz.model.*;

public interface QuestionsService {

     QuestionEntity GetQuestion(Game game, User user);
     AnswerResult AnswerQuestion(Answer answer);

}
