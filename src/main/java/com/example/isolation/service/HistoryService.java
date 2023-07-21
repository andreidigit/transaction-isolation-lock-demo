package com.example.isolation.service;

import com.example.isolation.entity.HistoryEntity;
import com.example.isolation.repository.HistoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepo historyRepo;

    public void saveLogHistory(String status, String name, int like) {
        historyRepo.save(HistoryEntity.builder().status(status).name(name).likes(like).build());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveLogHistoryTransactionRequired(String status, String name, int like) {
        historyRepo.save(HistoryEntity.builder().status(status).name(name).likes(like).build());
    }

}
