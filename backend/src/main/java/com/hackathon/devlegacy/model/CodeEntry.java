package com.hackathon.devlegacy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp; 

import java.time.LocalDateTime;

@Data
@Entity
public class CodeEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Column(length = 4000)
    private String originalCode;

    @Column(length = 4000)
    private String explanation;

    @Column(length = 4000)
    private String suggestions;

    @Column(length = 4000)
    private String generatedTests;

        @CreationTimestamp 
    @Column(nullable = false, updatable = false) 
    private LocalDateTime createdAt; 
    }
