package com.hackathon.devlegacy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.devlegacy.dto.CodeSubmissionRequest;
import com.hackathon.devlegacy.dto.GeminiResponse;
import com.hackathon.devlegacy.exception.GeminiApiException;
import com.hackathon.devlegacy.mapper.CodeEntryMapper;
import com.hackathon.devlegacy.model.CodeEntry;
import com.hackathon.devlegacy.repository.CodeEntryRepository;
import com.hackathon.devlegacy.service.GeminiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt; 
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/code-entries")
public class CodeEntryController {
    
    private static final Logger log = LoggerFactory.getLogger(CodeEntryController.class);

    private final GeminiService geminiService;
    private final CodeEntryRepository repository;
    private final ObjectMapper objectMapper;
    private final CodeEntryMapper codeEntryMapper;

    public CodeEntryController(GeminiService geminiService,
                               CodeEntryRepository repository,
                               ObjectMapper objectMapper,
                               CodeEntryMapper codeEntryMapper) {
        this.geminiService = geminiService;
        this.repository = repository;
        this.objectMapper = objectMapper;
        this.codeEntryMapper = codeEntryMapper;
    }

    @PostMapping
    public ResponseEntity<CodeEntry> analyzeAndSaveCode(
            @Valid @RequestBody CodeSubmissionRequest request,
            @AuthenticationPrincipal Jwt jwt) throws JsonProcessingException, GeminiApiException {

        String userId = jwt.getSubject();
        
        log.info("Received request to analyze and save code for user '{}'.", userId);

        String geminiJson = geminiService.analyzeCode(request.getCode());
        GeminiResponse geminiResponse = objectMapper.readValue(geminiJson, GeminiResponse.class);

        CodeEntry newEntry = codeEntryMapper.toEntity(request, geminiResponse, userId);
        CodeEntry savedEntry = repository.save(newEntry);
        
        log.info("Successfully saved new code entry with id {} for user '{}'.", savedEntry.getId(), userId);
        
        return ResponseEntity.ok(savedEntry);
    }

    @GetMapping
    public ResponseEntity<List<CodeEntry>> getHistory(
            @AuthenticationPrincipal Jwt jwt) {
        
        String userId = jwt.getSubject();
        log.info("Received request to fetch history for user '{}'.", userId);
        List<CodeEntry> entries = repository.findByUserId(userId);
        log.info("Found {} history entries for user '{}'.", entries.size(), userId);
        
        return ResponseEntity.ok(entries);
    }
    
    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        return Map.of("status", "UP");
    }
}

