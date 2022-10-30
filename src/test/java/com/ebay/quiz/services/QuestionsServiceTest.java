package com.ebay.quiz.services;

import com.ebay.quiz.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestionsServiceTest {

    private QuestionsService questionsService;

    @BeforeAll
    public void setUp() {
        questionsService = new QuestionsServiceImpl();
    }

    @Test
    void New_User_Get_New_Question() {
        UUID gameId = UUID.randomUUID();
        Game game = new Game(gameId);
        User user1 = new User("TestUser1");
        QuestionEntity question1 = questionsService.GetQuestion(game, user1);
        game.GetQuestions().add(question1);
        Answer answer = new Answer("TestUser1",game.GetId(),question1.getId(),question1.getRightAnswer());
        user1.getUserAnswers().add(answer);
        QuestionEntity question2 = questionsService.GetQuestion(game, user1);
        game.GetQuestions().add(question2);
        User user2 = new User("TestUser2");
        QuestionEntity question3 = questionsService.GetQuestion(game, user2);
        Assert.assertEquals(question1.getId(), question3.getId());
    }

    @Test
    void User_Get_Same_Question_If_Did_Not_Answer_It() {
        UUID gameId = UUID.randomUUID();
        String userName = "TestUser1";
        Game game = new Game(gameId);
        User user = new User(userName);
        QuestionEntity question1 = questionsService.GetQuestion(game, user);
        game.GetQuestions().add(question1);
        QuestionEntity question2 = questionsService.GetQuestion(game, user);
        Assert.assertEquals(question1.getId(), question2.getId());
    }

    @Test
    void If_User_Answer_Question_Get_New_Question() {
        UUID gameId = UUID.randomUUID();
        String userName = "TestUser1";
        Game game = new Game(gameId);
        User user = new User(userName);
        QuestionEntity question1 = questionsService.GetQuestion(game, user);
        Answer answer = new Answer("TestUser1",game.GetId(),question1.getId(),question1.getRightAnswer());
        user.getUserAnswers().add(answer);
        QuestionEntity question2 = questionsService.GetQuestion(game, user);
        Assert.assertNotEquals(question1.getId(), question2.getId());
    }

    /*
    @Test
    void Answer_Result_Right_Answer() {
        Question question = new Question();
        Answer answer = new Answer("", UUID.randomUUID(),question.getId(),question.getRightAnswer());
        AnswerResult answerResult = questionsService.GetAnswerResult(question,answer);
        Assert.assertEquals(AnswerStatus.Current,answerResult.getAnswerStatus());
        Assert.assertEquals(1,answerResult.getPointsEarned());
    }

    @Test
    void Answer_Result_Wrong_Answer() {
        Question question = new Question();
        Answer answer = new Answer("", UUID.randomUUID(),question.getId(),question.GetLightQuestion().getRightAnswer());
        AnswerResult answerResult = questionsService.GetAnswerResult(question,answer);
        Assert.assertEquals(AnswerStatus.Wrong,answerResult.getAnswerStatus());
        Assert.assertEquals(0,answerResult.getPointsEarned());
    }*/
}