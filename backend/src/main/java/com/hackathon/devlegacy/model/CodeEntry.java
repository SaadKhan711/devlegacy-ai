package com.hackathon.devlegacy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity // Marks this class as a database table
public class CodeEntry {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private Long id;

    private String userId;

    @Column(length = 4000) // Allows for longer code snippets
    private String originalCode;

    @Column(length = 4000)
    private String explanation;

    @Column(length = 4000)
    private String suggestions;

    @Column(length = 4000)
    private String generatedTests;

    private LocalDateTime createdAt = LocalDateTime.now();
}