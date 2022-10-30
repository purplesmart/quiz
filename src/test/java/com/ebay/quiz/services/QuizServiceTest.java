package com.ebay.quiz.services;

import com.ebay.quiz.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuizServiceTest {

    private QuizService quizService;
    private GameService gameService;
    private UsersService usersService;
    private QuestionsService questionsService;

    @BeforeAll
    public void setUp() {
        gameService = Mockito.mock(GameService.class);
        usersService = Mockito.mock(UsersService.class);
        questionsService = Mockito.mock(QuestionsService.class);
        quizService = new QuizServiceImpl(gameService,usersService,questionsService);
    }

    @Test
    void Get_Leaderboard_By_Game() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(gameService.GetLeaderboardByGame(uuid)).thenReturn(new Leaderboard(uuid));
        Leaderboard leaderboard = quizService.GetLeaderboardByGame(uuid);
        Assert.assertNotNull(leaderboard);
    }

    @Test
    void Create_Game() {
        Mockito.when(gameService.CreateGame()).thenReturn(new Game());
        Game game = quizService.CreateGame();
        Assert.assertNotNull(game);
        //Mockito.verify(gameService, Mockito.times(1)).createGame();
    }

    @Test
    void Get_Question() {
        UUID gameId = UUID.randomUUID();
        String userName = "TestUser";
        Game game = new Game(gameId);
        User user = new User(userName);
        Mockito.when(gameService.GetGame(gameId)).thenReturn(Optional.of(game));
        Mockito.when(usersService.GetUser(userName)).thenReturn(user);
        Mockito.when(questionsService.GetQuestion(game,user)).thenReturn(new QuestionEntity());
        Mockito.doNothing().when(gameService).AddQuestion(Mockito.any(),Mockito.any());
        Question question = quizService.GetQuestion(gameId,userName);
        Assert.assertNotNull(question);
        Mockito.verify(gameService, Mockito.times(1)).GetGame(gameId);
        Mockito.verify(questionsService, Mockito.times(1)).GetQuestion(game,user);
    }

    /*
    @Test
    void Answer_Question() {

        UUID gameId = UUID.randomUUID();
        Game game = new Game(gameId);
        Question question = new Question();
        game.GetQuestions().add(question);
        String userName = "TestUser";
        User user = new User(userName);

        Answer answer = new Answer(userName, gameId,question.getId(),question.getRightAnswer());

        Mockito.doNothing().when(usersService).UserAnswer(user,answer);
        Mockito.doNothing().when(gameService).AddUser(game,user);
        Mockito.when(gameService.GetGame(gameId)).thenReturn(Optional.of(game));
        Mockito.when(questionsService.GetAnswerResult(question,answer)).thenReturn(new AnswerResult(AnswerStatus.Current,1));

        quizService.AnswerQuestion(answer);

        Mockito.verify(gameService, Mockito.times(1)).GetGame(gameId);
        Mockito.verify(usersService, Mockito.times(1)).GetUser(userName);
        Mockito.verify(usersService, Mockito.times(1)).UserAnswer(Mockito.any(),Mockito.any());
        Mockito.verify(gameService, Mockito.times(1)).AddUser(Mockito.any(),Mockito.any());
    }*/
}