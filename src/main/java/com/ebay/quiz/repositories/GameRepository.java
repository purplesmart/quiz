package com.ebay.quiz.repositories;

import com.ebay.quiz.model.Game;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository  {
    Game save(Game game);
    Optional<Game> findById(UUID gameId);
}
