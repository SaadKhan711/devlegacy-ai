package com.hackathon.devlegacy.dto.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiResponsePayload {
    private List<Candidate> candidates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Candidate {
        private Content content;
    }

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

    // Helper methods to safely extract data
    public boolean hasContent() {
        return candidates != null && !candidates.isEmpty() &&
               candidates.get(0).getContent() != null &&
               candidates.get(0).getContent().getParts() != null &&
               !candidates.get(0).getContent().getParts().isEmpty() &&
               candidates.get(0).getContent().getParts().get(0).getText() != null;
    }

    public String extractText() {
        if (!hasContent()) {
            return null;
        }
        return candidates.get(0).getContent().getParts().get(0).getText();
    }
}
