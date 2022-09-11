package com.one.service.csv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CSV Reader Service should")
class CSVReaderServiceTest {

    private static final String QUIZ_PATH = "/QuizTest.csv";

    private CSVReaderService csvReaderService;

    @BeforeEach
    void setUp() {
        csvReaderService = new CSVReaderServiceImpl();
    }

    @Test
    @DisplayName("Should read CSV File")
    void readCSVFile() throws FileNotFoundException {
        assertNotNull(csvReaderService.readCSVFile(QUIZ_PATH));
    }

    @Test()
    @DisplayName("Should throw exception like file not found")
    void readCSVFileThrowFileNotFound() {
        assertThrows(FileNotFoundException.class, () -> csvReaderService.readCSVFile("2"),
            String.format("CSV file %s is not found!", QUIZ_PATH));
    }
}
