package com.one.configuration;

import com.one.service.ScannerService;
import com.one.service.ScannerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Scanner;

@PropertySource("classpath:app.properties")
@Configuration
public class AppConfig {

    @Bean
    public ScannerService scannerService() {
        return new ScannerServiceImpl(new Scanner(System.in));
    }
}
