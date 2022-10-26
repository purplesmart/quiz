package com.ebay.quiz.services;

import com.ebay.quiz.model.*;
import com.ebay.quiz.repositories.GameRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameServiceTest {

    private GameRepository gameRepository;
    private GameService gameService;

    @BeforeAll
    public void setUp() {
        gameRepository = Mockito.mock(GameRepository.class);
        gameService = new GameServiceImpl(gameRepository);
    }

    @Test
    void addUser() {
        Game game = new Game();
        User user = new User("TestUser");
        gameService.AddUser(game, user);
        assertEquals(1, game.GetUsers().size());
    }

    @Test
    void addQuestionToGame() {
        Game game = new Game();
        Question question = new Question();
        gameService.AddQuestion(game, question);
        assertEquals(1, game.GetQuestions().size());
    }

    @Test
    public void getLeaderboardByGame_ExpectedScore_0() {
        String userName = "TestUser";
        User user = new User(userName);
        Game game = GetDefinedGame(user, new AnswerStatus[]{AnswerStatus.Wrong,AnswerStatus.Wrong,AnswerStatus.Wrong});
        Mockito.when(gameRepository.findById(game.GetId())).thenReturn(Optional.of(game));
        Leaderboard leaderboard = gameService.GetLeaderboardByGame(game.GetId());
        assertEquals(0, leaderboard.getUsersScore().get(userName));
    }

    @Test
    public void getLeaderboardByGame_ExpectedScore_1() {
        String userName = "TestUser";
        User user = new User(userName);
        Game game = GetDefinedGame(user, new AnswerStatus[]{AnswerStatus.Current,AnswerStatus.Wrong,AnswerStatus.Wrong});
        Mockito.when(gameRepository.findById(game.GetId())).thenReturn(Optional.of(game));
        Leaderboard leaderboard = gameService.GetLeaderboardByGame(game.GetId());
        assertEquals(1, leaderboard.getUsersScore().get(userName));
    }

    @Test
    public void getLeaderboardByGame_ExpectedScore_2() {
        String userName = "TestUser";
        User user = new User(userName);
        Game game = GetDefinedGame(user, new AnswerStatus[]{AnswerStatus.Current,AnswerStatus.Current,AnswerStatus.Wrong});
        Mockito.when(gameRepository.findById(game.GetId())).thenReturn(Optional.of(game));
        Leaderboard leaderboard = gameService.GetLeaderboardByGame(game.GetId());
        assertEquals(2, leaderboard.getUsersScore().get(userName));
    }

    @Test
    public void getLeaderboardByGame_ExpectedScore_3() {
        String userName = "TestUser";
        User user = new User(userName);
        Game game = GetDefinedGame(user, new AnswerStatus[]{AnswerStatus.Current,AnswerStatus.Current,AnswerStatus.Current});
        Mockito.when(gameRepository.findById(game.GetId())).thenReturn(Optional.of(game));
        Leaderboard leaderboard = gameService.GetLeaderboardByGame(game.GetId());
        assertEquals(3, leaderboard.getUsersScore().get(userName));
    }

    private Game GetDefinedGame(User user, AnswerStatus[] answerStatuses){
        Game game = new Game();

        Arrays.stream(answerStatuses).forEach( answerStatus -> {
            Question question = new Question();
            Answer answer =
                    (answerStatus == AnswerStatus.Current)? GetRightAnswer(user.getUserName(), game.GetId(), question):
                            GetWrongAnswer(user.getUserName(), game.GetId(), question);
            user.getUserAnswers().add(answer);
            game.GetQuestions().add(question);
        });

        game.GetUsers().add(user);
        return game;
    }

    private Answer GetRightAnswer(String userName, UUID gameId, Question question){
        return new Answer(userName, gameId, question.getId(), question.getRightAnswer());
    }

    private Answer GetWrongAnswer(String userName, UUID gameId, Question question){
        final int MAX_POSSIBLE_ANSWERS = 4;
        return new Answer(userName, gameId, question.getId(), MAX_POSSIBLE_ANSWERS - 1 - question.getRightAnswer());
    }
}