package com.example.isolation.repository;

import com.example.isolation.entity.BuddyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuddyRepo extends JpaRepository<BuddyEntity, Long> {
    Optional<BuddyEntity> findByName(String name);
}
