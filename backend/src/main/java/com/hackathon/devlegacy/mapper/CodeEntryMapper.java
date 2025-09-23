package com.hackathon.devlegacy.mapper;

import com.hackathon.devlegacy.dto.CodeSubmissionRequest;
import com.hackathon.devlegacy.dto.GeminiResponse;
import com.hackathon.devlegacy.model.CodeEntry;
import org.springframework.stereotype.Component;

@Component
public class CodeEntryMapper {

    public CodeEntry toEntity(CodeSubmissionRequest request, GeminiResponse response, String userId) {
        CodeEntry entry = new CodeEntry();
        entry.setUserId(userId);
        entry.setOriginalCode(request.getCode());

        entry.setExplanation(response.getExplanation());

        if (response.getSuggestions() != null) {
            entry.setSuggestions(String.join("\n", response.getSuggestions()));
        } else {
            entry.setSuggestions("");
        }

        entry.setGeneratedTests(response.getGeneratedTests());
        return entry;
    }
}