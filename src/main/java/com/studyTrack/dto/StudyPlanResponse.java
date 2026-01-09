package com.studyTrack.dto;

import java.time.LocalDate;

import com.studyTrack.entity.StudyPlanStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StudyPlanResponse {

    private Long id;
    private String subject;
    private String topic;
    private Integer expectedMinutes;
    private LocalDate studyDate;
    private StudyPlanStatus status;
}

