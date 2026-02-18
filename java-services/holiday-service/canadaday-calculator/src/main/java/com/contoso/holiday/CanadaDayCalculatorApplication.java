package com.contoso.holiday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Canada Day Calculator microservice.
 * Migrated from COBOL AS400 program CANDAY01.CBLLE
 */
@SpringBootApplication
public class CanadaDayCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanadaDayCalculatorApplication.class, args);
    }
}
