package com.ebay.quiz.services;

import com.ebay.quiz.model.Answer;
import com.ebay.quiz.model.User;
import com.ebay.quiz.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public User GetUser(String userName) {
        Optional<User> user = usersRepository.findById(userName);
        if(user.isEmpty()){
            return usersRepository.save(new User(userName));
        }
        return user.get();
    }

    @Override
    public void UserAnswer(User user, Answer answer) {
        Optional<Answer> userAnswer =
                user
                        .getUserAnswers()
                        .stream()
                        .filter(ans -> ans.gameId.equals(answer.gameId) && ans.getQuestionId().equals(answer.getQuestionId())).findFirst();
        if (userAnswer.isEmpty()) {
            user.getUserAnswers().add(answer);
        } else {
            userAnswer.get().answerId = answer.answerId;
        }
        usersRepository.save(user);
    }
}
