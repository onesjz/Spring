package com.one.dao;

import com.one.model.Quiz;
import com.one.service.csv.CSVParserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizDaoImpl implements QuizDao {

    private final CSVParserService csvParserService;

    public QuizDaoImpl(CSVParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    @Override
    public List<Quiz> getQuizListByCSVFile(String csvFile) {
        return csvParserService.parse(csvFile, Quiz.class);
    }
}
