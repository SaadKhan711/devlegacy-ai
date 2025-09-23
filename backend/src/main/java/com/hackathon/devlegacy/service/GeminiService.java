package com.hackathon.devlegacy.service;

import com.hackathon.devlegacy.exception.GeminiApiException;

public interface GeminiService {
    String analyzeCode(String code) throws GeminiApiException;
}