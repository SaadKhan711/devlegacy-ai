package com.hackathon.devlegacy.dto.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequest {
    private List<Content> contents;
    private GenerationConfig generationConfig;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
        private List<Part> parts;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Part {
        private String text;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GenerationConfig {
        private String response_mime_type;
    }

    public static GeminiRequest fromString(String prompt) {
        Part part = new Part(prompt);
        Content content = new Content(List.of(part));
        GenerationConfig config = new GenerationConfig("application/json");
        return new GeminiRequest(List.of(content), config);
    }
}
