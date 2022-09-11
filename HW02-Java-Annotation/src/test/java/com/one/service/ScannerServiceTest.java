package com.one.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Scanner Service should")
class ScannerServiceTest {

    private ScannerService scannerService;

    @AfterEach
    void teardown() {
        scannerService.close();
    }

    @Test
    @DisplayName("should read input text")
    void readText() {
        String text = "qwe";
        scannerService = new ScannerServiceImpl(new Scanner(text));

        assertEquals(text, scannerService.readText());
    }

    @Test
    @DisplayName("should read input number")
    void readNumbers() {
        String number = "1";
        scannerService = new ScannerServiceImpl(new Scanner(number));

        assertEquals(1, scannerService.readNumbers());
    }
}
