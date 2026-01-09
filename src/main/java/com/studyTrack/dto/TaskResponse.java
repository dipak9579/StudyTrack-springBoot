package com.studyTrack.dto;

import java.time.LocalDate;

import com.studyTrack.entity.TaskPriority;
import com.studyTrack.entity.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
}

