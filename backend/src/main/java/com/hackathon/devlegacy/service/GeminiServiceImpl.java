package com.hackathon.devlegacy.service;

import com.hackathon.devlegacy.dto.gemini.GeminiRequest;
import com.hackathon.devlegacy.dto.gemini.GeminiResponsePayload;
import com.hackathon.devlegacy.exception.GeminiApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeminiServiceImpl implements GeminiService {

    private static final Logger log = LoggerFactory.getLogger(GeminiServiceImpl.class);

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public GeminiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://generativelanguage.googleapis.com").build();
    }

    @Override
    public String analyzeCode(String codeToAnalyze) throws GeminiApiException {
        String apiUrl = "/v1beta/models/gemini-1.5-flash:generateContent?key=" + geminiApiKey;

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

        GeminiRequest requestPayload = GeminiRequest.fromString(prompt);


        log.debug("Sending request to Gemini API. Prompt: {}", prompt);
        
        try {
            GeminiResponsePayload response = webClient.post()
                    .uri(apiUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestPayload)
                    .retrieve()
                    .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new GeminiApiException("Gemini API error: " + clientResponse.statusCode() + " " + errorBody)))
                    )
                    .bodyToMono(GeminiResponsePayload.class)
                    .block();

            if (response != null && response.hasContent()) {
                String responseText = response.extractText();
     
                log.debug("Received successful response from Gemini API: {}", responseText);
                                return responseText;
            } else {
                log.warn("Received an empty or invalid response payload from Gemini API.");
                                throw new GeminiApiException("Received an empty or invalid response from Gemini API.");
            }
        } catch (Exception e) {
           
            log.error("Failed to communicate with Gemini API: {}", e.getMessage());
            
            throw new GeminiApiException("Failed to communicate with Gemini API: " + e.getMessage(), e);
        }
    }
}

