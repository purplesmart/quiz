package com.ebay.quiz.repositories;

import com.ebay.quiz.model.Game;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GameRepositoryImpl implements GameRepository {

    private Map<UUID, Game> gameMap;

    public GameRepositoryImpl() {
        this.gameMap = new HashMap<>();
    }

    @Override
    public Game save(Game game) {
        UUID gameId = game.GetId();
        gameMap.put(gameId, game);
        return gameMap.get(gameId);
    }

    @Override
    public Optional<Game> findById(UUID gameId) {
        if (gameMap.containsKey(gameId)) {
            return Optional.of(gameMap.get(gameId));
        } else {
            return Optional.empty();
        }
    }
}
