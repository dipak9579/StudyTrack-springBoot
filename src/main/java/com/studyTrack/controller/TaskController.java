package com.studyTrack.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyTrack.dto.TaskCreateRequest;
import com.studyTrack.dto.TaskResponse;
import com.studyTrack.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

	private final TaskService taskService;
	
	@PostMapping
	public ResponseEntity<TaskResponse>createTask(@Valid @RequestBody TaskCreateRequest request){
		return ResponseEntity.ok(taskService.createTask(request));
	}
	
	@GetMapping
	public ResponseEntity<List<TaskResponse>>getAllTasks(){
		return ResponseEntity.ok(taskService.getAllTasks());
	}
	
	@GetMapping("/today")
	public ResponseEntity<List<TaskResponse>>getTodayTasks(){
		return ResponseEntity.ok(taskService.getTodayTasks());
	}
	
	@PutMapping("/{id}/complete")
	public ResponseEntity<TaskResponse>completeTask(@PathVariable Long id){
		return ResponseEntity.ok(taskService.markTaskCompleted(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void>deleteTask(@PathVariable Long id){
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
