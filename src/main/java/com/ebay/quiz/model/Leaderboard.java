package com.ebay.quiz.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Leaderboard {

    private  UUID gameId;
    private Map<String, Integer> usersScore;

    public Leaderboard(UUID gameId){
        this.gameId = gameId;
        usersScore = new HashMap<>();
    }

    public Map<String, Integer> getUsersScore() {
        return usersScore;
    }

}
