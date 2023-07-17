package com.example.isolation.service;

import com.example.isolation.entity.BuddyEntity;
import com.example.isolation.repository.BuddyRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class BuddyService {
    private final BuddyRepo buddyRepo;
    private final HistoryService historyService;

    public void addLikeToBuddy(@NonNull String name, int like) {
        buddyRepo.findByName(name).ifPresentOrElse(
                body -> {
                    body.setLikes(body.getLikes() + like);
                    historyService.saveLogHistory(like > 0 ? "ADD" : "SUB", name, body.getLikes());
                },
                () -> {
                    log.warn("There is no buddy with Name {}", name);
                    throw new IllegalArgumentException("There is no buddy with Name: " + name);
                }
        );
    }

    public void createBuddy(String name) {
        buddyRepo.findByName(name).ifPresentOrElse(
                buddy -> log.info("buddy {} is all ready exist", buddy.getName()),
                () -> {
                    try {
                        buddyRepo.save(BuddyEntity.builder().name(name).likes(0).build());
                    } catch (RuntimeException e) {
                        log.warn("failed saving buddy {}. ", name, e);
                    }
                }
        );
    }
}
