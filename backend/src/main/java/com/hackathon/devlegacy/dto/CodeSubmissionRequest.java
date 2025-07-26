package com.hackathon.devlegacy.dto;

import lombok.Data;

@Data
public class CodeSubmissionRequest {
    private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}