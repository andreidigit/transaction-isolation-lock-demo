package com.example.isolation;

import com.example.isolation.service.BuddyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IsolationApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsolationApplication.class, args);
    }

    @Bean
    public CommandLineRunner setBuddy(BuddyService buddyService) {
        return args -> buddyService.createBuddy("Ivan");
    }
}
