package com.ebay.quiz.services;

import com.ebay.quiz.model.Answer;
import com.ebay.quiz.model.User;
import com.ebay.quiz.repositories.UsersRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersServiceTest {

    private UsersRepository usersRepository;
    private UsersService usersService;

    @BeforeAll
    public void setUp() {
        usersRepository = Mockito.mock(UsersRepository.class);
        usersService = new UsersServiceImpl(usersRepository);
    }

    @Test
    void When_User_Not_Exist_Create_New_User() {
        Mockito.when(usersRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(new User());
        User user = usersService.GetUser("TestUser");
        Assert.assertNotNull(user);
    }

    @Test
    void Add_User_One_Answer() {
        String UserName = "TestUser";
        User user = new User(UserName);
        Answer answer = new Answer(UserName, UUID.randomUUID(), UUID.randomUUID(), 1);
        usersService.UserAnswer(user, answer);
        Assert.assertEquals(1, user.getUserAnswers().size());
    }

    @Test
    void Add_User_Two_Answer() {
        String UserName = "TestUser";
        User user = new User(UserName);
        Answer answer1 = new Answer(UserName, UUID.randomUUID(), UUID.randomUUID(), 1);
        usersService.UserAnswer(user, answer1);
        Answer answer2 = new Answer(UserName, UUID.randomUUID(), UUID.randomUUID(), 1);
        usersService.UserAnswer(user, answer2);
        Assert.assertEquals(2, user.getUserAnswers().size());
    }
}