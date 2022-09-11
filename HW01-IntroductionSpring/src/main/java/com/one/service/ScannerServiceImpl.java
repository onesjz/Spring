package com.one.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ScannerServiceImpl implements ScannerService {

    private static final Logger log = LoggerFactory.getLogger(ScannerServiceImpl.class);

    private final Scanner scanner;

    public ScannerServiceImpl() {
        this.scanner = new Scanner(System.in);
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
    public void close() {
        scanner.close();
    }
}
