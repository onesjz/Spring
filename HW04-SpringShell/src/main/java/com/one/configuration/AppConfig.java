package com.one.configuration;

import com.one.properties.AppProperty;
import com.one.service.IOService;
import com.one.service.IOServiceStream;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
@EnableConfigurationProperties(AppProperty.class)
public class AppConfig {

    private static final String SLASH = "/";
    private static final String UNDERSCORE = "_";

    private final AppProperty appProperty;

    public AppConfig(AppProperty appProperty) {
        this.appProperty = appProperty;
    }

    @Bean
    @SuppressWarnings("java:S106")
    public IOService ioService() {
        return new IOServiceStream(new Scanner(System.in), System.out);
    }

    @Bean
    public String filePath() {
        return SLASH + appProperty.getCsvProperty().getDirectory() +
            SLASH + appProperty.getLocale().getLanguage().toLowerCase() +
            UNDERSCORE + appProperty.getCsvProperty().getFile();
    }
}
