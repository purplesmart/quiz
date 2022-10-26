package com.ebay.quiz.controllers;

import com.ebay.quiz.model.*;
import com.ebay.quiz.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/getquestion/{username}/{gameid}")
    public ResponseEntity<Question> getQuestion(@PathVariable(value = "gameid") UUID gameId, @PathVariable(value = "username") String userName) {
        try {
            return Optional
                    .ofNullable(quizService.GetQuestion(gameId, userName))
                    .map(question -> ResponseEntity.ok().body(question))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/answerquestion")
    public ResponseEntity<AnswerResult> answerQuestion(@RequestBody Answer answer) {
        try {
            return Optional
                    .ofNullable(quizService.AnswerQuestion(answer))
                    .map(answerQuestion -> ResponseEntity.ok().body(answerQuestion))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getleaderboardbygame/{gameid}")
    public ResponseEntity<Leaderboard> getLeaderboardByGame(@PathVariable(value = "gameid") UUID gameId) {
        try {
            return Optional
                    .ofNullable(quizService.GetLeaderboardByGame(gameId))
                    .map(leaderboard -> ResponseEntity.ok().body(leaderboard))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/creategame")
    public ResponseEntity<Game> createGame() {
        try {
            return Optional
                    .ofNullable(quizService.CreateGame())
                    .map(game -> ResponseEntity.ok().body(game))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
