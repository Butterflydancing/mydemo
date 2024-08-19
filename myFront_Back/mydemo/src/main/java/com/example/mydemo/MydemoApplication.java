package com.example.mydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class MydemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(MydemoApplication.class);

    public static void main(String[] args) {
        logger.info("Starting MydemoApplication...");
        SpringApplication.run(MydemoApplication.class, args);
        logger.info("MydemoApplication started successfully.");
    }

}
