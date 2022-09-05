package com.one.service;

import com.one.dao.QuizDao;
import com.one.model.Guest;
import com.one.model.Quiz;

import java.util.List;

public class QuizServiceImpl implements QuizService {

    private static final String YES_INPUT = "y";

    private final ScannerService scannerService;
    private final QuizDao quizDao;
    private final GuestService guestService;

    public QuizServiceImpl(ScannerService scannerService, QuizDao quizDao, GuestService guestService) {
        this.scannerService = scannerService;
        this.quizDao = quizDao;
        this.guestService = guestService;
    }

    @Override
    public void startQuiz() {
        int score = 0;

        System.out.println("Welcome to the simple quiz!");
        System.out.println("Please, to write your name: ");
        Guest currentGuest = guestService.saveGuest(scannerService.readText());
        System.out.println("Congratulation! You are checked in!");

        System.out.println("Let's start.");
        List<Quiz> quizList = quizDao.getQuizList();

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
        guestService.updateScore(currentGuest.getName(), score);
        System.out.println("---------");

        for(Guest guest : guestService.getScoreboard()) {
            System.out.println(guest.toString());
        }

        System.out.println("Restart (y/n)?");
        if(YES_INPUT.equals(scannerService.readText().toLowerCase().trim())) {
            this.startQuiz();
        } else {
            scannerService.close();
            System.out.println("Goodbye!");
        }
    }
}
