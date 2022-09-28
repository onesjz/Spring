package com.one.service.csv;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class CSVReaderServiceImpl implements CSVReaderService {

    private static final Logger log = LoggerFactory.getLogger(CSVReaderServiceImpl.class);

    @Override
    public CSVReader readCSVFile(String fileName) throws FileNotFoundException {
        InputStream inputStream = getInputStreamByResource(fileName);

        if (inputStream == null) {
            log.error("CSV file {} is not found!", fileName);
            throw new FileNotFoundException(String.format("CSV file %s is not found!", fileName));
        }

        return new CSVReader(new InputStreamReader(inputStream));
    }

    private InputStream getInputStreamByResource(String name) {
        return getClass().getResourceAsStream(name);
    }
}
