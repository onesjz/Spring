package com.one.dao;

import com.one.service.csv.CSVParserService;
import com.one.model.Quiz;

import java.util.List;

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
