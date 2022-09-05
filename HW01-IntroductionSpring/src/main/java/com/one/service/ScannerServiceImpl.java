package com.one.service;

import java.util.Scanner;

public class ScannerServiceImpl implements ScannerService {

    private final Scanner scanner;

    public ScannerServiceImpl() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readInput() {
        return scanner.nextLine().toLowerCase().trim();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
