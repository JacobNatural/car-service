package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The {@code CarsSpringBootJpaApplication} class is the main entry point for the Spring Boot application.
 * It is annotated with {@code @SpringBootApplication}, which enables auto-configuration,
 * component scanning, and configuration properties scanning for the application.
 *
 * The {@code main} method uses {@link SpringApplication#run} to launch the application.
 *
 * This class serves as the starting point for the Cars Spring Boot application.
 */
@SpringBootApplication
public class CarsSpringBootJpaApplication {

    /**
     * The main method that runs the Spring Boot application.
     * It initializes the application context and starts the embedded web server.
     *
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {
        SpringApplication.run(CarsSpringBootJpaApplication.class, args);
    }
}
