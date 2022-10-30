package com.ebay.quiz.services;

import com.ebay.quiz.model.*;
import com.ebay.quiz.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game CreateGame() {
        return gameRepository.save(new Game());
    }

    @Override
    public Optional<Game> GetGame(UUID gameId) {
        return gameRepository.findById(gameId);
    }

    @Override
    public void AddUser(Game game, User user) {
        if(!game.GetUsers().stream().anyMatch(usr -> usr.userName.equals(user.userName))){
            game.GetUsers().add(user);
            gameRepository.save(game);
        }
    }

    @Override
    public void AddQuestion(Game game, QuestionEntity question) {
        if(!game.GetQuestions().stream().anyMatch(qstn -> qstn.Id.equals(question.Id))) {
            game.GetQuestions().add(question);
            gameRepository.save(game);
        }
    }

    @Override
    public Leaderboard GetLeaderboardByGame(UUID gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isEmpty()) {
            return null;
        }
        Leaderboard leaderboard = new Leaderboard(gameId);
        game.get()
                .GetUsers()
                .stream()
                .forEach(user -> leaderboard
                        .getUsersScore()
                        .put(user.getUserName(), GetScore(user.getUserAnswers(), game.get().GetQuestions())));
        return leaderboard;
    }

    private Integer GetScore(List<Answer> userAnswers, List<QuestionEntity> questions) {
        return userAnswers
                .stream()
                .mapToInt(answer ->
                        {
                            Optional<QuestionEntity> questionOption =
                                    questions
                                            .stream()
                                            .filter(question -> question.getId().equals(answer.getQuestionId())).findFirst();
                            if (questionOption.isPresent() && questionOption.get().getRightAnswer() == answer.getAnswerId()) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                ).sum();
    }
}
