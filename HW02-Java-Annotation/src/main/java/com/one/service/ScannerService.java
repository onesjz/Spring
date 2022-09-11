package com.one.service;

public interface ScannerService {
    String readText();

    int readNumbers();

    void close();
}
