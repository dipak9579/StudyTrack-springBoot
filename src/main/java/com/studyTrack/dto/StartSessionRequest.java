package com.studyTrack.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StartSessionRequest {

    @NotBlank
    private String subject;
}
