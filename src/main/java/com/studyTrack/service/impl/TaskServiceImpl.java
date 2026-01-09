package com.studyTrack.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.studyTrack.dto.TaskCreateRequest;
import com.studyTrack.dto.TaskResponse;
import com.studyTrack.entity.Task;
import com.studyTrack.entity.TaskStatus;
import com.studyTrack.entity.User;
import com.studyTrack.exception.ResourceNotFoundException;
import com.studyTrack.exception.UnauthorizedActionException;
import com.studyTrack.repository.TaskRepository;
import com.studyTrack.repository.UserRepository;
import com.studyTrack.service.TaskService;
import com.studyTrack.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
	private final TaskRepository taskRepository;
	private final UserRepository userRepository;
	
	private User getCurrentUser() {
		String email=SecurityUtil.getCurrentUserEmail();
		return userRepository.findByEmail(email)
				.orElseThrow(()->new ResourceNotFoundException("User not found"));
	
	}
	
	@Override
	public TaskResponse createTask(TaskCreateRequest request) {
		User user=getCurrentUser();
		Task task=Task.builder()
				.title(request.getTitle())
				.description(request.getDescription())
				.priority(request.getPriority())
				.status(TaskStatus.PENDING)
				.dueDate(request.getDueDate())
				.user(user)
				.build();
		
		return mapToResponse(taskRepository.save(task));
	}

	@Override
	public List<TaskResponse> getAllTasks() {
		
		return taskRepository.findByUserId(getCurrentUser().getId())
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public List<TaskResponse> getTodayTasks() {
		
		return taskRepository.findByUserIdAndDueDate(getCurrentUser().getId(), LocalDate.now())
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public TaskResponse markTaskCompleted(Long taskId) {
		Task task=taskRepository.findById(taskId).orElseThrow(()->new ResourceNotFoundException("Task not found"));
;
		
		if(!task.getUser().getId().equals(getCurrentUser().getId())) {
			throw new UnauthorizedActionException("You cannot modify this task");

		}
		task.setStatus(TaskStatus.COMPLETED);
		return mapToResponse(taskRepository.save(task));
	}

	@Override
	public void deleteTask(Long taskId) {
		Task task=taskRepository.findById(taskId)
				.orElseThrow(()->new ResourceNotFoundException("Task not found"));

		
		if(!task.getUser().getId().equals(getCurrentUser().getId())) {
			throw new UnauthorizedActionException("You cannot modify this task");

		}
		
		taskRepository.delete(task);
	}
	
	
	private TaskResponse mapToResponse(Task task) {
		return TaskResponse.builder()
				.id(task.getId())
				.title(task.getTitle())
				.description(task.getDescription())
				.status(task.getStatus())
				.priority(task.getPriority())
				.dueDate(task.getDueDate())
				.build();
	}

	
}
