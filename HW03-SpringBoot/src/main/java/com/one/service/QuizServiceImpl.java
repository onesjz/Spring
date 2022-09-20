package com.one.service;

import com.one.dao.QuizDao;
import com.one.model.Guest;
import com.one.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private static final String YES_INPUT = "y";

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
    public void startQuiz() {
        ioService.printText(messageService.getMessage("welcome"));
        Guest currentGuest = createUser();
        ioService.printText(messageService.getMessage("congratulations", currentGuest.getFirstName()));

        ioService.printText(messageService.getMessage("quiz.start"));
        int score = quiz();

        ioService.printText(messageService.getMessage("quiz.result", currentGuest.getFirstName()));
        guestService.updateScore(currentGuest.getId(), score);

        showResults(guestService.getAllGuests());

        ioService.printText(messageService.getMessage("restart"));
        if (YES_INPUT.equals(ioService.readText().toLowerCase().trim())) {
            this.startQuiz();
        } else {
            ioService.close();
            ioService.printText(messageService.getMessage("goodbye"));
        }
    }

    private Guest createUser() {
        ioService.printText(messageService.getMessage("create.user.name"));
        String firstName = ioService.readText();
        ioService.printText(messageService.getMessage("create.user.lastname"));
        String lastName = ioService.readText();
        return guestService.saveGuest(firstName, lastName);
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

    private void showResults(List<Guest> guests) {
        ioService.printText("-------------------");
        String format = "| %1$-15s |\n";

        for (Guest guest : guests) {
            ioService.printFormattedText(format,
                messageService.getMessage("users.result",
                    guest.getFirstName(), guest.getLastName(), String.valueOf(guest.getScore())));
        }
        ioService.printText("-------------------");
    }
}
