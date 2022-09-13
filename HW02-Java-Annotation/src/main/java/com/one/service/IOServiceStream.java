package com.one.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceStream implements IOService {

    private static final Logger log = LoggerFactory.getLogger(IOServiceStream.class);

    private final Scanner scanner;
    private final PrintStream printStream;

    public IOServiceStream(Scanner scanner, PrintStream printStream) {
        this.scanner = scanner;
        this.printStream = printStream;
    }

    @Override
    public String readText() {
        return scanner.nextLine();
    }

    @Override
    public int readNumbers() {
        String number = scanner.nextLine().trim();

        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            log.debug("Invalid input.", e);
            return 0;
        }
    }

    @Override
    public void printText(String text) {
        printStream.println(text);
    }

    @Override
    public void printFormattedText(String format, String text) {
        printStream.format(format, text);
    }

    @Override
    public void close() {
        scanner.close();
    }
}
