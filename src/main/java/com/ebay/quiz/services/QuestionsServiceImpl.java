package com.ebay.quiz.services;

import com.ebay.quiz.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Override
    public Question GetQuestion(Game game, User user) {
        List<Question> questions =
                game.GetQuestions().stream().filter(
                        question ->
                                !user.getUserAnswers().stream()
                                        .anyMatch(answer -> answer.getQuestionId().equals(question.getId()))).toList();
        if (questions.size() == 0) {
            return new Question();
        } else {
            return questions.get(0).GetLightQuestion();
        }
    }

    @Override
    public AnswerResult GetAnswerResult(Question question, Answer answer) {
        if (question.getRightAnswer() == answer.getAnswerId()) {
            return new AnswerResult(AnswerStatus.Current, 1);
        } else {
            return new AnswerResult(AnswerStatus.Wrong, 0);
        }
    }
}
