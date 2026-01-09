package com.studyTrack.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.studyTrack.dto.StudyPlanRequest;
import com.studyTrack.dto.StudyPlanResponse;
import com.studyTrack.entity.StudyPlan;
import com.studyTrack.entity.StudyPlanStatus;
import com.studyTrack.entity.User;
import com.studyTrack.repository.StudyPlanRepository;
import com.studyTrack.repository.UserRepository;
import com.studyTrack.service.StudyPlanService;
import com.studyTrack.util.SecurityUtil;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class StudyPlanServiceImpl implements StudyPlanService {

    private final StudyPlanRepository studyPlanRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public StudyPlanResponse createTodayPlan(
            StudyPlanRequest request) {

        User user = getCurrentUser();

        StudyPlan plan = StudyPlan.builder()
                .subject(request.getSubject())
                .topic(request.getTopic())
                .expectedMinutes(request.getExpectedMinutes())
                .studyDate(LocalDate.now())
                .status(StudyPlanStatus.PLANNED)
                .user(user)
                .build();

        return mapToResponse(
                studyPlanRepository.save(plan));
    }

    @Override
    public List<StudyPlanResponse> getTodayPlans() {

        return studyPlanRepository.findByUserIdAndStudyDate(
                        getCurrentUser().getId(),
                        LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudyPlanResponse markPlanCompleted(Long planId) {

        StudyPlan plan = studyPlanRepository.findById(planId)
                .orElseThrow(() ->
                        new RuntimeException("Plan not found"));

        if (!plan.getUser().getId()
                .equals(getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        plan.setStatus(StudyPlanStatus.COMPLETED);
        return mapToResponse(
                studyPlanRepository.save(plan));
    }

    private StudyPlanResponse mapToResponse(StudyPlan plan) {
        return StudyPlanResponse.builder()
                .id(plan.getId())
                .subject(plan.getSubject())
                .topic(plan.getTopic())
                .expectedMinutes(plan.getExpectedMinutes())
                .studyDate(plan.getStudyDate())
                .status(plan.getStatus())
                .build();
    }
}
