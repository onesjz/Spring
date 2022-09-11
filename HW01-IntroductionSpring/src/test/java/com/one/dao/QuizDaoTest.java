package com.one.dao;

import com.one.model.Quiz;
import com.one.service.csv.CSVParserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("QuizDao should")
class QuizDaoTest {

    private QuizDao quizDao;
    private CSVParserService csvParserService;

    @BeforeEach
    void setUp() {
        csvParserService = mock(CSVParserService.class);
        quizDao = new QuizDaoImpl(csvParserService);
    }

    @Test
    @DisplayName("Should get quiz list")
    void getQuizList() {
        when(csvParserService.parse("csv", Quiz.class)).thenReturn(Collections.singletonList(new Quiz()));

        List<Quiz> quizList = quizDao.getQuizListByCSVFile("csv");

        assertEquals(1, quizList.size());
    }
}
