package com.ebay.quiz.services;

import com.ebay.quiz.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class QuizServiceImpl implements QuizService{

    public QuizServiceImpl(GameService gameService, UsersService usersService, QuestionsService questionsService) {
        this.gameService = gameService;
        this.usersService = usersService;
        this.questionsService = questionsService;
    }

    @Autowired
    private GameService gameService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private QuestionsService questionsService;

    public Leaderboard GetLeaderboardByGame(UUID gameId) {

        return gameService.GetLeaderboardByGame(gameId);
    }

    public Game CreateGame() {
        return gameService.CreateGame();
    }

    public Question GetQuestion(UUID gameId, String userName){

        Optional<Game> game = gameService.GetGame(gameId);
        if(game.isEmpty()){
            return null;
        }
        User user = usersService.GetUser(userName);
        QuestionEntity question = questionsService.GetQuestion(game.get(),user);
        gameService.AddQuestion(game.get(),question);
        return question.GetLightQuestion();
    }

    public AnswerResult AnswerQuestion(Answer answer) {

        Optional<Game> game = gameService.GetGame(answer.getGameId());
        if(game.isEmpty()){
            return null;
        }
        return questionsService.AnswerQuestion(answer);
    }

    /*
    public AnswerResult AnswerQuestion(Answer answer) {

        Optional<Game> game = gameService.GetGame(answer.getGameId());
        if(game.isEmpty()){
            return null;
        }

        Optional<Question> relevantQuestion =
                game.get()
                        .GetQuestions()
                        .stream()
                        .filter(question -> question.getId().equals(answer.getQuestionId())).findFirst();
        if(relevantQuestion.isEmpty()){
            return null;
        }

        User user =  usersService.GetUser(answer.getUserName());
        usersService.UserAnswer(user, answer);
        gameService.AddUser(game.get(), user);
        return questionsService.GetAnswerResult(relevantQuestion.get(), answer);
    }*/
}
