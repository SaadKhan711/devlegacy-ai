package com.hackathon.devlegacy.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CodeEntryResponse {
    private Long id;
    private String userId;
    private String originalCode;
    private String explanation;
    private String suggestions;
    private String generatedTests;
    private LocalDateTime createdAt;
}
