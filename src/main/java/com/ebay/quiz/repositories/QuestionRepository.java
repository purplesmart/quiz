package com.ebay.quiz.repositories;

import com.ebay.quiz.model.*;
import java.util.List;
import java.util.UUID;

public interface QuestionRepository {

    QuestionEntity save(QuestionEntity question);

    void answerQuestion(Answer answer);

    List<Answer> getAnswers(UUID questionId, UUID gameId);

    QuestionEntity GetQuestion(UUID questionId);

}
