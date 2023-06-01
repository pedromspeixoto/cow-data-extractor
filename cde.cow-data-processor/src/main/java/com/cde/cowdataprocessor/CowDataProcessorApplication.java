package com.cde.cowdataprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CowDataProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CowDataProcessorApplication.class, args);
    }

}