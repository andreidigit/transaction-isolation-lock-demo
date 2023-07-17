package com.example.isolation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="history")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="buddy_name")
    private String name;

    private int likes;

    private String status;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
}
