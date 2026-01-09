package com.studyTrack.dto;

import java.time.LocalDate;

import com.studyTrack.entity.TaskPriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskCreateRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private TaskPriority priority;

    private LocalDate dueDate;
}

