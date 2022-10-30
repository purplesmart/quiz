package com.ebay.quiz.logic;

import com.ebay.quiz.model.*;
import com.ebay.quiz.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.lang.Integer;
import static java.util.stream.Collectors.groupingBy;

@Component
public class AnswerResolverImpl implements AnswerResolver {

    @Autowired
    private QuestionRepository questionRepository;

    public AnswerResolverImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public AnswerResult AnswerQuestion(Answer answer) {
        final int MIN_ANSWERS = 6;
        final int THRESHOLD = 9;
        final int MAX_ANSWERS = 11;
        questionRepository.answerQuestion(answer);
        QuestionEntity question = questionRepository.GetQuestion(answer.getQuestionId());
        List<Answer> answers = questionRepository.getAnswers(answer.getQuestionId(),answer.getGameId());
        if(answers.size() >= MIN_ANSWERS && answers.size() <= MAX_ANSWERS){
            Map.Entry<Integer, List<Answer>> topAnswer = GetTopAnswer(answers);
            if(topAnswer.getValue().size() >= THRESHOLD){
                question.setAnswerStatus(AnswerStatus.Resolved);
                question.setRightAnswer(topAnswer.getKey());
            }
        }else if (answers.size() > MAX_ANSWERS && question.getAnswerStatus() == AnswerStatus.Pending){
            question.setAnswerStatus(AnswerStatus.Unresolved);
        }
        questionRepository.save(question);
        int pointsEarned =
                (question.getAnswerStatus() == AnswerStatus.Pending
                        || question.getAnswerStatus() == AnswerStatus.Unresolved)?0:
                        (question.getRightAnswer() == answer.getAnswerId())?1:0;
        return new AnswerResult(question.getAnswerStatus(),pointsEarned);
    }

    private Map.Entry<Integer, List<Answer>> GetTopAnswer(List<Answer> answers){
        Map<Integer, List<Answer>> groupsByAnswer = answers.stream().collect(groupingBy(Answer::getAnswerId));
        Map.Entry<Integer, List<Answer>> topAnswer = null;

        for (Map.Entry<Integer, List<Answer>> entry : groupsByAnswer.entrySet())
            if (topAnswer == null ||  entry.getValue().size() > topAnswer.getValue().size()){
                topAnswer = entry;
            }
        return topAnswer;
    }



    /*
    @Override
    public AnswerResult GetAnswerResult(Answer answer) {
        questionRepository.answerQuestion(answer);
        List<Answer> answers = questionRepository.getAnswers(answer.getQuestionId(), answer.getGameId());

        if (answers.size() < 6) {
            return new AnswerResult(AnswerStatus.Pending, 0);
        }

        Map.Entry<Integer, List<Answer>> topAnswer =  GetTopAnswer(answers);

        if(IsResolved(answers, topAnswer)) {
            int pointsEarned = (topAnswer.getKey().equals(answer.answerId))?1:0;
            return new AnswerResult(AnswerStatus.Resolved, pointsEarned);
        }
        else{
            return new AnswerResult(AnswerStatus.Unresolved, 0);
        }
    }

    private boolean IsResolved(List<Answer> answers, Map.Entry<Integer, List<Answer>> topAnswer) {
        int THRESHOLD = 9;
        int MAX_ANSWERS = 11;
        return answers.size() <= MAX_ANSWERS && topAnswer.getValue().size() >= THRESHOLD;
    }

    private Map.Entry<Integer, List<Answer>> GetTopAnswer(List<Answer> answers){
        Map<Integer, List<Answer>> groupsByAnswer = answers.stream().collect(groupingBy(Answer::getAnswerId));
        Map.Entry<Integer, List<Answer>> topAnswer = null;

        for (Map.Entry<Integer, List<Answer>> entry : groupsByAnswer.entrySet())
            if (topAnswer == null ||  entry.getValue().size() > topAnswer.getValue().size()){
                topAnswer = entry;
            }
        return topAnswer;
    }
*/
}