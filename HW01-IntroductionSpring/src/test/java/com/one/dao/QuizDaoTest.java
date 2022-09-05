package com.one.dao;

import com.one.model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("QuizDao should")
class QuizDaoTest {

    private static final String QUIZ_PATH = "/QuizTest.csv";
    private QuizDao quizDao;

    @BeforeEach
    void setUp() {
        quizDao = new QuizDaoImpl(QUIZ_PATH);
    }

    @Test
    @DisplayName("Should get quiz list")
    void getQuizList() {
        List<Quiz> quizList = quizDao.getQuizList();

        assertEquals(2, quizList.size());
        assertEquals("q1", quizList.get(0).getQuestion());
        assertEquals(2, quizList.get(0).getCorrectAnswer());
        assertEquals(3, quizList.get(0).getAnswers().size());
    }
}
