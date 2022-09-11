package com.one.service.csv;

import java.util.List;

public interface CSVParserService {
    <T> List<T> parse(String fileName, Class<T> clazz);
}
