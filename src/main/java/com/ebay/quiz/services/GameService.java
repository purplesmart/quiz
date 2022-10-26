package com.ebay.quiz.services;

import com.ebay.quiz.model.*;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;


public interface GameService {

    Game CreateGame();

    Optional<Game> GetGame(UUID gameId);

    void AddUser(Game game, User user);

    void AddQuestion(Game game, Question question);

    Leaderboard GetLeaderboardByGame(UUID gameId);
}
