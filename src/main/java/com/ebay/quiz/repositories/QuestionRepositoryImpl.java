package com.ebay.quiz.repositories;

import com.ebay.quiz.model.Answer;
import com.ebay.quiz.model.QuestionEntity;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository{

    private Map<UUID, QuestionEntity> questions;
    private Map<UUID,Map<UUID, List<Answer>>> questionAnswers;

    public QuestionRepositoryImpl() {
        questions = new HashMap<>();
        questionAnswers = new HashMap<>();
    }

    @Override
    public QuestionEntity save(QuestionEntity question) {
        addQuestion(question);
        return questions.get(question.getId());
    }

    @Override
    public void answerQuestion(Answer answer) {
        UUID questionId = answer.getQuestionId();
        if(knownQuestion(questionId)){
            if(questionAnswers.get(questionId).get(answer.getGameId()) == null){
                questionAnswers.get(questionId).put(answer.getGameId(), new ArrayList<>());
            }
            questionAnswers.get(questionId).get(answer.getGameId()).add(answer);
        }else{
            throw new NoSuchElementException("Question Unknown");
        }
    }

    @Override
    public List<Answer> getAnswers(UUID questionId, UUID gameId) {
        if(knownQuestion(questionId) && questionAnswers.get(questionId).containsKey(gameId)){
            return questionAnswers.get(questionId).get(gameId);
        }
        return null;
    }

    @Override
    public QuestionEntity GetQuestion(UUID questionId) {
        return questions.get(questionId);
    }

    private boolean knownQuestion(UUID questionId){
        return questions.containsKey(questionId) && questionAnswers.containsKey(questionId);
    }

    private void addQuestion(QuestionEntity question){
        UUID questionId = question.getId();
        if(!knownQuestion(questionId)){
            if(!questions.containsKey(questionId)) {
                questions.put(questionId, question);
            }
            if(!questionAnswers.containsKey(questionId)){
                questionAnswers.put(questionId, new HashMap<>());
            }
        }
    }
}
