package com.one.service.csv;

import java.io.FileNotFoundException;

public interface CSVReaderService {
    com.opencsv.CSVReader readCSVFile(String path) throws FileNotFoundException;
}
