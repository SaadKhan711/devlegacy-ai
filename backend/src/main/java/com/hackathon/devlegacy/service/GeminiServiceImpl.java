package com.hackathon.devlegacy.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String analyzeCode(String codeToAnalyze) {
                String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + geminiApiKey;

                String prompt = """
                Analyze the following Java code snippet. Provide the output in a clean JSON format with three keys: "explanation", "suggestions", and "generatedTests".

                - "explanation": A clear, concise explanation of what the code does.
                - "suggestions": A list of potential improvements or best practices.
                - "generatedTests": A basic JUnit 5 test case for the code.

                Here is the code:
                ```java
                %s
                ```
                """.formatted(codeToAnalyze);
        
        String requestBody = """
                {
                  "contents": [
                    {
                      "parts": [
                        {
                          "text": "%s"
                        }
                      ]
                    }
                  ],
                  "generationConfig": {
                    "response_mime_type": "application/json"
                  }
                }
                """.formatted(prompt.replace("\"", "\\\"")); 

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        
                JsonNode response = restTemplate.postForObject(apiUrl, entity, JsonNode.class);

        if (response != null && response.has("candidates")) {
            return response.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
        }

        throw new RuntimeException("Failed to get a valid response from Gemini API");
    }
}