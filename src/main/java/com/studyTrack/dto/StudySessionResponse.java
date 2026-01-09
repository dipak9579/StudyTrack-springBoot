package com.studyTrack.dto;

import java.time.LocalDateTime;

import com.studyTrack.entity.StudySessionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StudySessionResponse {

    private Long id;
    private String subject;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long totalMinutes;
    private StudySessionStatus status;
}

