package com.one.service;

import com.one.properties.AppProperty;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final AppProperty appProperty;
    private final MessageSource messageSource;

    public MessageServiceImpl(MessageSource messageSource, AppProperty appProperty) {
        this.messageSource = messageSource;
        this.appProperty = appProperty;
    }

    @Override
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, appProperty.getLocale());
    }

    @Override
    public String getMessage(String code, String arg) {
        return messageSource.getMessage(code, new Object[]{arg}, appProperty.getLocale());
    }

    @Override
    public String getMessage(String code, String... args) {
        return messageSource.getMessage(code, args, appProperty.getLocale());
    }
}
