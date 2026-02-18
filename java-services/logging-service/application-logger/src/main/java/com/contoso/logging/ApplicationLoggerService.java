package com.contoso.logging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Application Logging microservice.
 * Migrated from COBOL AS400 program LOG0010CB.cblle
 */
@SpringBootApplication
public class ApplicationLoggerService {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationLoggerService.class, args);
    }
}
