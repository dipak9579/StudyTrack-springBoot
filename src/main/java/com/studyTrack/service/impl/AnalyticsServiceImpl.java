package com.studyTrack.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.studyTrack.dto.DailyProgressResponse;
import com.studyTrack.entity.StudySession;
import com.studyTrack.entity.StudySessionStatus;
import com.studyTrack.entity.TaskStatus;
import com.studyTrack.entity.User;
import com.studyTrack.repository.StudySessionRepository;
import com.studyTrack.repository.TaskRepository;
import com.studyTrack.repository.UserRepository;
import com.studyTrack.service.AnalyticsService;
import com.studyTrack.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final TaskRepository taskRepository;
    private final StudySessionRepository sessionRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public DailyProgressResponse getTodayProgress() {

        User user = getCurrentUser();
        LocalDate today = LocalDate.now();

        // Study time
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        long totalMinutes =
        	    sessionRepository.findByUserIdAndStartTimeBetween(
        	            user.getId(), start, end)
        	        .stream()
        	        .filter(s -> s.getStatus() == StudySessionStatus.COMPLETED) // 🔥
        	        .filter(s -> s.getTotalMinutes() != null)
        	        .mapToLong(StudySession::getTotalMinutes)
        	        .sum();


        // Tasks
        long completed =
                taskRepository.findByUserIdAndStatus(
                        user.getId(), TaskStatus.COMPLETED)
                        .size();

        long totalTasks =
                taskRepository.findByUserId(user.getId()).size();

        long pending = totalTasks - completed;

        // Productivity score
        int productivity = calculateProductivity(
                totalMinutes, completed);

        return DailyProgressResponse.builder()
                .date(today)
                .totalStudyMinutes(totalMinutes)
                .tasksCompleted(completed)
                .tasksPending(pending)
                .productivityScore(productivity)
                .build();
    }

    @Override
    public List<DailyProgressResponse> getWeeklyProgress() {

        List<DailyProgressResponse> responses =
                new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            responses.add(buildProgressForDate(date));
        }
        return responses;
    }

   
    private DailyProgressResponse buildProgressForDate(LocalDate date) {

        User user = getCurrentUser();

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        long minutes =
        	    sessionRepository.findByUserIdAndStartTimeBetween(
        	            user.getId(), start, end)
        	        .stream()
        	        .filter(s -> s.getStatus() == StudySessionStatus.COMPLETED) // 🔥
        	        .filter(s -> s.getTotalMinutes() != null)
        	        .mapToLong(StudySession::getTotalMinutes)
        	        .sum();


        long completedTasks =
            taskRepository.findByUserIdAndStatus(
                    user.getId(), TaskStatus.COMPLETED)
                .size();

        int productivity =
            calculateProductivity(minutes, completedTasks);

        return DailyProgressResponse.builder()
                .date(date)
                .totalStudyMinutes(minutes)
                .tasksCompleted(completedTasks)
                .tasksPending(0L)
                .productivityScore(productivity)
                .build();
    }

    
    private int calculateProductivity(
            long studyMinutes, long completedTasks) {

        int score = 0;

        if (studyMinutes >= 120) score += 50;
        else if (studyMinutes >= 60) score += 30;

        score += Math.min(completedTasks * 10, 50);

        return Math.min(score, 100);
    }
}
