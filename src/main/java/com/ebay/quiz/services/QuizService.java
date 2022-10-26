package com.ebay.quiz.services;

import com.ebay.quiz.model.*;
import java.util.UUID;

public interface QuizService {

    Leaderboard GetLeaderboardByGame(UUID gameId);
    Game CreateGame();
    Question GetQuestion(UUID gameId, String userName);
    AnswerResult AnswerQuestion(Answer answer);

}
