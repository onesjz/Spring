package com.one.service;

public interface MessageService {
    String getMessage(String code);

    String getMessage(String code, String arg);

    String getMessage(String code, String... args);
}
