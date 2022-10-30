package com.ebay.quiz.logic;

import com.ebay.quiz.model.Answer;
import com.ebay.quiz.model.AnswerResult;

public interface AnswerResolver {
    AnswerResult AnswerQuestion(Answer answer);
}
