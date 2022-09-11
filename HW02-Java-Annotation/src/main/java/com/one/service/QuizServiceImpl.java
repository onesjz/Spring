package com.one.service;

import com.one.dao.QuizDao;
import com.one.model.Guest;
import com.one.model.Quiz;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("java:S106")
public class QuizServiceImpl implements QuizService {

    private static final String YES_INPUT = "y";

    @Value("${csv.file.name}")
    private String csvFile;
    private final ScannerService scannerService;
    private final QuizDao quizDao;
    private final GuestService guestService;

    public QuizServiceImpl(ScannerService scannerService,
                           QuizDao quizDao,
                           GuestService guestService) {
        this.scannerService = scannerService;
        this.quizDao = quizDao;
        this.guestService = guestService;
    }

    @Override
    public void startQuiz() {
        System.out.println("Welcome to the simple quiz!");
        Guest currentGuest = createUser();
        System.out.println("Congratulation! You are checked in!");

        System.out.println("Let's start.");
        int score = quiz();

        System.out.println("Updating result!");
        guestService.updateScore(currentGuest.getId(), score);

        showResults(guestService.getAllGuests());

        System.out.println("Restart (y/n)?");
        if (YES_INPUT.equals(scannerService.readText().toLowerCase().trim())) {
            this.startQuiz();
        } else {
            scannerService.close();
            System.out.println("Goodbye!");
        }
    }

    private Guest createUser() {
        System.out.println("Please, to write your name: ");
        String firstName = scannerService.readText();
        System.out.println("Then, to write your last name: ");
        String lastName = scannerService.readText();
        return guestService.saveGuest(firstName, lastName);
    }

    private int quiz() {
        int score = 0;

        List<Quiz> quizList = quizDao.getQuizListByCSVFile(csvFile);

        for (Quiz quiz : quizList) {
            System.out.println("Choose the correct answer: ");
            System.out.println(quiz.getQuestion());
            for (String answer : quiz.getAnswers()) {
                System.out.println(answer);
            }

            if (scannerService.readNumbers() == quiz.getCorrectAnswer()) {
                score++;
            }
        }
        return score;
    }

    private void showResults(List<Guest> guests) {
        System.out.println("-------------------");
        String format = "| %1$-15s |\n";

        for (Guest guest : guests) {
            System.out.format(format, guest.toString());
        }
        System.out.println("-------------------");
    }
}
