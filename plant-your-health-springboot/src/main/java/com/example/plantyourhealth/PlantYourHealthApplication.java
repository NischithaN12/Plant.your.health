package com.example.plantyourhealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlantYourHealthApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlantYourHealthApplication.class, args);
    }
}

