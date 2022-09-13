package com.one.configuration;

import com.one.service.IOService;
import com.one.service.IOServiceStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Scanner;

@PropertySource("classpath:app.properties")
@Configuration
public class AppConfig {

    @Bean
    @SuppressWarnings("java:S106")
    public IOService scannerService() {
        return new IOServiceStream(new Scanner(System.in), System.out);
    }
}
