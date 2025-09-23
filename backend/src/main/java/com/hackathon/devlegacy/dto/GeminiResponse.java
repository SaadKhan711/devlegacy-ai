package com.hackathon.devlegacy.dto;

import lombok.Data;
import java.util.List;


@Data
public class GeminiResponse {
    private String explanation;
    private List<String> suggestions;
    private String generatedTests;
}
