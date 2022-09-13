package com.one.service;

import com.one.dao.QuizDao;
import com.one.model.Guest;
import com.one.model.Quiz;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private static final String YES_INPUT = "y";

    @Value("${csv.file.name}")
    private String csvFile;
    private final IOService ioService;
    private final QuizDao quizDao;
    private final GuestService guestService;

    public QuizServiceImpl(IOService ioService,
                           QuizDao quizDao,
                           GuestService guestService) {
        this.ioService = ioService;
        this.quizDao = quizDao;
        this.guestService = guestService;
    }

    @Override
    public void startQuiz() {
        ioService.printText("Welcome to the simple quiz!");
        Guest currentGuest = createUser();
        ioService.printText("Congratulation! You are checked in!");

        ioService.printText("Let's start.");
        int score = quiz();

        ioService.printText("Updating result!");
        guestService.updateScore(currentGuest.getId(), score);

        showResults(guestService.getAllGuests());

        ioService.printText("Restart (y/n)?");
        if (YES_INPUT.equals(ioService.readText().toLowerCase().trim())) {
            this.startQuiz();
        } else {
            ioService.close();
            ioService.printText("Goodbye!");
        }
    }

    private Guest createUser() {
        ioService.printText("Please, to write your name: ");
        String firstName = ioService.readText();
        ioService.printText("Then, to write your last name: ");
        String lastName = ioService.readText();
        return guestService.saveGuest(firstName, lastName);
    }

    private int quiz() {
        int score = 0;

        List<Quiz> quizList = quizDao.getQuizListByCSVFile(csvFile);

        for (Quiz quiz : quizList) {
            ioService.printText("Choose the correct answer: ");
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
            ioService.printFormattedText(format, guest.toString());
        }
        ioService.printText("-------------------");
    }
}
