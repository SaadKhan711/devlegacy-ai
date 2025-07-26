package com.hackathon.devlegacy.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data // Lombok: This automatically creates getters, setters, toString, etc.
@Document(collection = "code_entries") // Tells Spring this is a MongoDB document
public class CodeEntry {

    @Id
    private String id;

    private String userId; // We'll use this later for Auth0
    private String originalCode;
    private String explanation;
    private String suggestions;
    private String generatedTests;
    private LocalDateTime createdAt = LocalDateTime.now();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOriginalCode() {
		return originalCode;
	}
	public void setOriginalCode(String originalCode) {
		this.originalCode = originalCode;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public String getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}
	public String getGeneratedTests() {
		return generatedTests;
	}
	public void setGeneratedTests(String generatedTests) {
		this.generatedTests = generatedTests;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}