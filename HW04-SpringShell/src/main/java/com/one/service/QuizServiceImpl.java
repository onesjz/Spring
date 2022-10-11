package com.one.service;

import com.one.dao.QuizDao;
import com.one.model.Guest;
import com.one.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final MessageService messageService;
    private final IOService ioService;
    private final QuizDao quizDao;
    private final GuestService guestService;
    private final String filePath;

    public QuizServiceImpl(MessageService messageService,
                           IOService ioService,
                           QuizDao quizDao,
                           GuestService guestService,
                           String filePath) {
        this.messageService = messageService;
        this.ioService = ioService;
        this.quizDao = quizDao;
        this.guestService = guestService;
        this.filePath = filePath;
    }

    @Override
    public void startQuiz(String userId) {
        Guest guest = guestService.getUser(userId);

        ioService.printText(messageService.getMessage("quiz.start"));
        int score = quiz();

        ioService.printText(messageService.getMessage("quiz.result", guest.getFirstName()));
        guestService.updateScore(guest.getId(), score);
    }

    private int quiz() {
        int score = 0;

        List<Quiz> quizList = quizDao.getQuizListByCSVFile(filePath);

        for (Quiz quiz : quizList) {
            ioService.printText(messageService.getMessage("quiz.answer"));
            ioService.printText(quiz.getQuestion());
            for (String answer : quiz.getAnswers()) {
                ioService.printText(answer);
            }

            if (ioService.readNumbers() == quiz.getCorrectAnswer()) {
                score++;
            }
        }
        return score;
    }
}
