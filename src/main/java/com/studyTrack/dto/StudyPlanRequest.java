package com.studyTrack.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudyPlanRequest {

    @NotBlank
    private String subject;

    private String topic;

    @NotNull
    @Min(10)
    private Integer expectedMinutes;
}

