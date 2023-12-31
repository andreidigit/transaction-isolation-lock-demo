package com.example.isolation;

import com.example.isolation.service.BuddyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRetry
@EnableScheduling
public class IsolationApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsolationApplication.class, args);
    }

    @Bean
    public CommandLineRunner setBuddy(BuddyService buddyService) {
        return args -> buddyService.createBuddy("Ivan", 0);
    }

    @Bean
    public CommandLineRunner setBuddyV(BuddyService buddyService) {
        return args -> buddyService.createBuddyV("Ivan", 0);
    }
}
