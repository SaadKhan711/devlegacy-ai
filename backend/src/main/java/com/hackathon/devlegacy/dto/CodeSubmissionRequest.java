package com.hackathon.devlegacy.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class CodeSubmissionRequest {

    @NotBlank(message = "Code cannot be empty.")
    @Size(min = 10, max = 4000, message = "Code must be between 10 and 4000 characters.")
    private String code;
}
