package com.one.service.csv;

import com.one.service.csv.CSVReaderService;
import com.one.service.csv.CSVReaderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CSV Reader Service should")
class CSVReaderServiceTest {

    private static final String CSV_FILE = "/csv/QuizTest.csv";

    private CSVReaderService csvReaderService;

    @BeforeEach
    void init() {
        csvReaderService = new CSVReaderServiceImpl();
    }

    @Test
    @DisplayName("should read *.csv file")
    void readCSVFile() throws FileNotFoundException {
        assertNotNull(csvReaderService.readCSVFile(CSV_FILE));
    }

    @Test
    @DisplayName("should throw exception file is not found")
    void readCSVFileThrowFileNotFound() {
        assertThrows(FileNotFoundException.class, () -> csvReaderService.readCSVFile("123"),
            String.format("CSV file %s is not found!", CSV_FILE));
    }
}
