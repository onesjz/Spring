package com.one.service.csv;

import com.one.model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVParserServiceTest {

    private static final String QUIZ_PATH = "/QuizTest.csv";

    private CSVParserService csvParserService;

    @BeforeEach
    void setUp() {
        csvParserService = new CSVParserServiceImpl(new CSVReaderServiceImpl());
    }

    @Test
    void parse() {
        List<Quiz> quizList = csvParserService.parse(QUIZ_PATH, Quiz.class);

        assertEquals(2, quizList.size());
        assertEquals("q1", quizList.get(0).getQuestion());
        assertEquals(3, quizList.get(0).getAnswers().size());
        assertEquals(2, quizList.get(1).getCorrectAnswer());
    }
}
