package com.ebay.quiz.controllers;

import com.ebay.quiz.model.*;
import com.ebay.quiz.services.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuizController.class)
class QuizControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private QuizService quizService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void Get_Question_Return_Question() throws Exception {

        UUID gameId = UUID.randomUUID();
        String userName = "TestUser";

        Mockito.when(quizService.GetQuestion(gameId,userName)).thenReturn(new Question());

        mvc.perform(get("/quiz/getquestion/" + userName + "/" + gameId))
                .andExpect(status().isOk());
    }

    @Test
    void Get_Question_Return_Null() throws Exception {

        UUID gameId = UUID.randomUUID();
        String userName = "TestUser";

        Mockito.when(quizService.GetQuestion(gameId,userName)).thenReturn(null);

        mvc.perform(get("/quiz/getquestion/" + userName + "/" + gameId))
                .andExpect(status().isNotFound());
    }

    @Test
    void AnswerQuestion() throws Exception {
        Answer answer = new Answer("TestUser", UUID.randomUUID(), UUID.randomUUID(), 1);
        Mockito.when(quizService.AnswerQuestion(Mockito.any())).thenReturn(new AnswerResult(AnswerStatus.Current, 1));

        mvc.perform(post("/quiz/answerquestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(answer)))
                .andExpect(status().isOk());
    }

    @Test
    void Get_Leaderboard_Return_Game()  throws Exception{

        UUID gameId = UUID.randomUUID();
        Mockito.when(quizService.GetLeaderboardByGame(gameId)).thenReturn(new Leaderboard(gameId));
        mvc.perform(get("/quiz/getleaderboardbygame/" + gameId))
                .andExpect(status().isOk());
    }

    @Test
    void Get_Leaderboard_Return_Null()  throws Exception{

        UUID gameId = UUID.randomUUID();
        Mockito.when(quizService.GetLeaderboardByGame(gameId)).thenReturn(null);
        mvc.perform(get("/quiz/getleaderboardbygame/" + gameId))
                .andExpect(status().isNotFound());

    }

    @Test
    void Create_Game_Return_Game() throws Exception {

        Mockito.when(quizService.CreateGame()).thenReturn(new Game());
        mvc.perform(post("/quiz/creategame"))
                .andExpect(status().isOk());
    }

    @Test
    void Create_Game_Return_Null() throws Exception {

        Mockito.when(quizService.CreateGame()).thenReturn(null);
        mvc.perform(post("/quiz/creategame"))
                .andExpect(status().isNotFound());
    }
}