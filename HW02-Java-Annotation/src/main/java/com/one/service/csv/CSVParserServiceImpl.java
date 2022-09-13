package com.one.service.csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CSVParserServiceImpl implements CSVParserService {

    private static final Logger log = LoggerFactory.getLogger(CSVParserServiceImpl.class);

    private final CSVReaderService csvReaderService;

    public CSVParserServiceImpl(CSVReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

    @Override
    public <T> List<T> parse(String fileName, Class<T> clazz) {
        try (CSVReader csvReader = csvReaderService.readCSVFile(fileName)) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                .withSeparator(',')
                .withType(clazz)
                .build();

            return csvToBean.parse();
        } catch (Exception e) {
            log.error("The document had a parsing problem.", e);
        }
        return Collections.emptyList();
    }
}
