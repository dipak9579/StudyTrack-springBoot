package com.studyTrack.dto;

import java.time.LocalDateTime;

import com.studyTrack.entity.StudySessionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudySessionResponse {

    private Long id;

    private String subject;

    private StudySessionStatus status;

    // Used when restoring active session
    private Long elapsedSeconds;

    // Used when session is stopped/completed
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long totalMinutes;
}
