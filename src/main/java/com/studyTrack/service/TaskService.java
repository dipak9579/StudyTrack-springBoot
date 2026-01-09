package com.studyTrack.service;

import java.util.List;

import com.studyTrack.dto.TaskCreateRequest;
import com.studyTrack.dto.TaskResponse;

public interface TaskService {
	
	TaskResponse createTask(TaskCreateRequest request);
	List<TaskResponse>getAllTasks();
	List<TaskResponse>getTodayTasks();
	TaskResponse markTaskCompleted(Long taskId);
	void deleteTask(Long taskId);

}
