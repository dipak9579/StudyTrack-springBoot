package com.studyTrack.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyTrack.entity.Task;
import com.studyTrack.entity.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task>findByUserId(Long userId);
	List<Task>findByUserIdAndStatus(Long userId,TaskStatus status);
	List<Task>findByUserIdAndDueDate(Long userId,LocalDate date);
	
}
