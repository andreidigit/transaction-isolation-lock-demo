package com.example.isolation.service;

import com.example.isolation.entity.BuddyEntity;
import com.example.isolation.entity.BuddyVersionEntity;
import com.example.isolation.repository.BuddyRepo;
import com.example.isolation.repository.BuddyVersionRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Log4j2
@RequiredArgsConstructor
public class BuddyService {
    private final BuddyRepo buddyRepo;
    private final BuddyVersionRepo buddyVersionRepo;
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

    public void createBuddy(String name, int like) {
        buddyRepo.findByName(name).ifPresentOrElse(
                buddy -> log.info("buddy {} is all ready exist", buddy.getName()),
                () -> {
                    try {
                        buddyRepo.save(BuddyEntity.builder().name(name).likes(like).build());
                    } catch (RuntimeException e) {
                        log.warn("failed saving buddy {}. ", name, e);
                    }
                }
        );
    }

    @Transactional
    public void createBuddyV(String name, int like) {
        buddyVersionRepo.findByName(name).ifPresentOrElse(
                buddyV -> log.info("buddyV {} is all ready exist", buddyV.getName()),
                () -> {
                    try {
                        buddyVersionRepo.save(BuddyVersionEntity.builder().name(name).likes(like).build());
                    } catch (RuntimeException e) {
                        log.warn("failed saving buddy {}. ", name, e);
                    }
                }
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addLikeToBuddyDefault(@NonNull String name, int like) {
        buddyRepo.findByName(name).ifPresentOrElse(
                body -> {
                    body.setLikes(body.getLikes() + like);
                    historyService.saveLogHistoryTransactionRequired(like > 0 ? "ADD" : "SUB", name, body.getLikes());
                },
                () -> {
                    log.warn("There is no buddy with Name {}", name);
                    throw new IllegalArgumentException("There is no buddy with Name: " + name);
                }
        );
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(retryFor = SQLException.class, maxAttempts = 15)
    public void addLikeToBuddyRepeatableRead(@NonNull String name, int like) {
        buddyRepo.findByName(name).ifPresentOrElse(
                body -> {
                    body.setLikes(body.getLikes() + like);
                    historyService.saveLogHistoryTransactionRequired(like > 0 ? "ADD" : "SUB", name, body.getLikes());
                },
                () -> {
                    log.warn("There is no buddy with Name {}", name);
                    throw new IllegalArgumentException("There is no buddy with Name: " + name);
                }
        );
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(retryFor = SQLException.class, maxAttempts = 15)
    public void addLikeToBuddySerializable(@NonNull String name, int like) {
        buddyRepo.findByName(name).ifPresentOrElse(
                body -> {
                    body.setLikes(body.getLikes() + like);
                    historyService.saveLogHistoryTransactionRequired(like > 0 ? "ADD" : "SUB", name, body.getLikes());
                },
                () -> {
                    log.warn("There is no buddy with Name {}", name);
                    throw new IllegalArgumentException("There is no buddy with Name: " + name);
                }
        );
    }

    @Transactional
    @Retryable(maxAttempts = 15)
    public void addLikeToBuddyVersioned(@NonNull String name, int like) {
        buddyVersionRepo.findByName(name).ifPresentOrElse(
                body -> {
                    body.setLikes(body.getLikes() + like);
                    historyService.saveLogHistoryTransactionRequired(like > 0 ? "ADD" : "SUB", name, body.getLikes());
                },
                () -> {
                    log.warn("There is no buddy with Name {}", name);
                    throw new IllegalArgumentException("There is no buddy with Name: " + name);
                }
        );
    }

    @Transactional
    @Retryable(maxAttempts = 5)
    public void addLikeToBuddyPessimistic(@NonNull String name, int like) {
        buddyRepo.findByNameIs(name).ifPresentOrElse(
                body -> {
                    body.setLikes(body.getLikes() + like);
                    historyService.saveLogHistoryTransactionRequired(like > 0 ? "ADD" : "SUB", name, body.getLikes());
                },
                () -> {
                    log.warn("There is no buddy with Name {}", name);
                    throw new IllegalArgumentException("There is no buddy with Name: " + name);
                }
        );
    }
}
