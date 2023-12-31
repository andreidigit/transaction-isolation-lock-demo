package com.example.isolation.repository;

import com.example.isolation.entity.BuddyEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuddyRepo extends JpaRepository<BuddyEntity, Long> {
    Optional<BuddyEntity> findByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BuddyEntity> findByNameIs(String name);
}
