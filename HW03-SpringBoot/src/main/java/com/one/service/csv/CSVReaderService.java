package com.one.service.csv;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;

public interface CSVReaderService {
    CSVReader readCSVFile(String path) throws FileNotFoundException;
}
