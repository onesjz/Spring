package com.one.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Scanner Service should")
class IOServiceTest {

    private IOService ioService;

    @AfterEach
    void teardown() {
        ioService.close();
    }

    @Test
    @DisplayName("should read input text")
    void readText() {
        String text = "qwe";
        ioService = new IOServiceStream(new Scanner(text), System.out);

        assertEquals(text, ioService.readText());
    }

    @Test
    @DisplayName("should read input number")
    void readNumbers() {
        String number = "1";
        ioService = new IOServiceStream(new Scanner(number), System.out);

        assertEquals(1, ioService.readNumbers());
    }
}
