package com.example.isolation.repository;

import com.example.isolation.entity.BuddyVersionEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuddyVersionRepo extends JpaRepository<BuddyVersionEntity, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<BuddyVersionEntity> findByName(String name);
}
