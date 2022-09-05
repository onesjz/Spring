package com.one.dao;

import com.one.model.Quiz;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class QuizDaoImpl implements QuizDao {

    private static final Logger log = LoggerFactory.getLogger(QuizDaoImpl.class);

    private final String csvPath;

    public QuizDaoImpl(String csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public List<Quiz> getQuizList() {

        try(CSVReader csvReader = new CSVReader(new InputStreamReader(getCsvFile()))) {
            CsvToBean<Quiz> csvToBean = new CsvToBeanBuilder<Quiz>(csvReader)
                .withSeparator(',')
                .withType(Quiz.class)
                .build();

            return csvToBean.parse();
        } catch (Exception e) {
            log.error("The document {} had a parsing problem.", csvPath, e);
        }
        return Collections.emptyList();
    }

    private InputStream getCsvFile() {
        return getClass().getResourceAsStream(csvPath);
    }
}
