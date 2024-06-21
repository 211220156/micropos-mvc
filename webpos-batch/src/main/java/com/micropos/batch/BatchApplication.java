package com.micropos.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "batch");
        SpringApplication.run(BatchApplication.class, args);
    }
}
