package com.one.service.csv;

import com.one.model.Quiz;
import com.one.service.csv.CSVParserService;
import com.one.service.csv.CSVParserServiceImpl;
import com.one.service.csv.CSVReaderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("CSV Parser Service should")
class CSVParserServiceTest {

    private static final String CSV_FILE = "/csv/QuizTest.csv";

    private CSVParserService csvParserService;

    @BeforeEach
    void init() {
        csvParserService = new CSVParserServiceImpl(new CSVReaderServiceImpl());
    }

    @Test
    @DisplayName("should parse csv file")
    void parse() {
        List<Quiz> quizList = csvParserService.parse(CSV_FILE, Quiz.class);

        assertFalse(quizList.isEmpty());
        assertEquals("q1", quizList.get(0).getQuestion());
        assertEquals(2, quizList.get(1).getCorrectAnswer());
        assertEquals(3, quizList.get(0).getAnswers().size());
    }
}
