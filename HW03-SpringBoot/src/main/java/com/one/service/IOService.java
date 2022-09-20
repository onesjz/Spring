package com.one.service;

public interface IOService {
    String readText();

    int readNumbers();

    void printText(String text);

    void printFormattedText(String format, String text);

    void close();
}
