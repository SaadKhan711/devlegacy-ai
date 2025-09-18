package com.hackathon.devlegacy.dto;

import lombok.Data;
import java.util.List;

@Data
public class GeminiResponse {
    private String explanation;
    private List<String> suggestions;
    private String generatedTests;
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public List<String> getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}
	public String getGeneratedTests() {
		return generatedTests;
	}
	public void setGeneratedTests(String generatedTests) {
		this.generatedTests = generatedTests;
	}
}