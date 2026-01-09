package com.studyTrack.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DailyProgressResponse {

    private LocalDate date;
    private Long totalStudyMinutes;
    private Long tasksCompleted;
    private Long tasksPending;
    private Integer productivityScore;
}
