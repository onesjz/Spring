package com.one.dao;

import com.one.model.Quiz;
import com.one.service.csv.CSVParserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Quiz Dao should")
class QuizDaoTest {

    private QuizDao quizDao;
    private CSVParserService csvParserService;

    @BeforeEach
    void init() {
        csvParserService = mock(CSVParserService.class);
        quizDao = new QuizDaoImpl(csvParserService);
    }

    @Test
    @DisplayName("should get quiz list by csv file name")
    void getQuizListByCSVFile() {
        when(csvParserService.parse("csv", Quiz.class)).thenReturn(Collections.singletonList(new Quiz()));

        assertNotNull(quizDao.getQuizListByCSVFile("csv"));
    }
}
