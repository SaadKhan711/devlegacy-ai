package com.hackathon.devlegacy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.devlegacy.dto.CodeSubmissionRequest;
import com.hackathon.devlegacy.dto.GeminiResponse;
import com.hackathon.devlegacy.model.CodeEntry;
import com.hackathon.devlegacy.repository.CodeEntryRepository;
import com.hackathon.devlegacy.service.GeminiService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/code-entries")
@CrossOrigin({"http://localhost:5173", "https://lively-churros-737f1e.netlify.app"}) // Make sure this matches your frontend URL
public class CodeEntryController {

    private final GeminiService geminiService;
    private final CodeEntryRepository repository;
    private final ObjectMapper objectMapper;

    public CodeEntryController(GeminiService geminiService, CodeEntryRepository repository, ObjectMapper objectMapper) {
        this.geminiService = geminiService;
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    // --- UPDATED POST ENDPOINT ---
    @PostMapping
    public ResponseEntity<CodeEntry> analyzeAndSaveCode(
            @RequestBody CodeSubmissionRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        try {
            // Manually decode the JWT to get the user ID
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            DecodedJWT jwt = JWT.decode(token);
            String userId = jwt.getSubject(); // 'sub' claim is the user ID

            // 1. Get analysis from Gemini
            String geminiJson = geminiService.analyzeCode(request.getCode());

            // 2. Convert the JSON string from Gemini into our Java object
            GeminiResponse geminiResponse = objectMapper.readValue(geminiJson, GeminiResponse.class);

            // 3. Create a new database entry with the REAL user ID
            CodeEntry newEntry = new CodeEntry();
            newEntry.setUserId(userId); // Use the real user ID
            newEntry.setOriginalCode(request.getCode());
            newEntry.setExplanation(geminiResponse.getExplanation());
            newEntry.setSuggestions(String.join("\n", geminiResponse.getSuggestions()));
            newEntry.setGeneratedTests(geminiResponse.getGeneratedTests());

            // 4. Save to MongoDB
            CodeEntry savedEntry = repository.save(newEntry);

            // 5. Return the saved entry to the client
            return ResponseEntity.ok(savedEntry);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/health")
public Map<String, String> healthCheck() {
    return Map.of("status", "UP");
}

    // --- NEW GET ENDPOINT FOR HISTORY ---
    @GetMapping
    public ResponseEntity<List<CodeEntry>> getHistory(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        try {
            String token = authHeader.substring(7);
            DecodedJWT jwt = JWT.decode(token);
            String userId = jwt.getSubject();

            List<CodeEntry> entries = repository.findByUserId(userId);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}