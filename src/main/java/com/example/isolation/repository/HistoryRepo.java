package com.example.isolation.repository;

import com.example.isolation.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepo extends JpaRepository<HistoryEntity, Long> {
}
