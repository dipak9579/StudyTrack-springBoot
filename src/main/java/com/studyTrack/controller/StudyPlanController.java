package com.studyTrack.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyTrack.dto.StudyPlanRequest;
import com.studyTrack.dto.StudyPlanResponse;
import com.studyTrack.service.StudyPlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/study-plans")
@RequiredArgsConstructor
public class StudyPlanController {

    private final StudyPlanService studyPlanService;

    @PostMapping("/today")
    public ResponseEntity<StudyPlanResponse> createTodayPlan(
            @Valid @RequestBody StudyPlanRequest request) {
        return ResponseEntity.ok(
                studyPlanService.createTodayPlan(request));
    }

    @GetMapping("/today")
    public ResponseEntity<List<StudyPlanResponse>> getTodayPlans() {
        return ResponseEntity.ok(
                studyPlanService.getTodayPlans());
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<StudyPlanResponse> completePlan(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                studyPlanService.markPlanCompleted(id));
    }
}
