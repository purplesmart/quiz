package com.ebay.quiz.services;

import com.ebay.quiz.logic.AnswerResolver;
import com.ebay.quiz.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private AnswerResolver answerResolver;

    @Override
    public QuestionEntity GetQuestion(Game game, User user) {
        List<QuestionEntity> questions =
                game.GetQuestions().stream().filter(
                        question ->
                                !user.getUserAnswers().stream()
                                        .anyMatch(answer -> answer.getQuestionId().equals(question.getId()))).toList();
        if (questions.size() == 0) {
            return new QuestionEntity();
        } else {
            return questions.get(0);
        }
    }

    @Override
    public AnswerResult AnswerQuestion(Answer answer) {
        return answerResolver.AnswerQuestion(answer);
    }
}
