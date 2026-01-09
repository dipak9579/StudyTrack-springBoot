package com.studyTrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyTrack.dto.StreakResponse;
import com.studyTrack.service.GamificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gamification")
@RequiredArgsConstructor
public class GamificationController {

    private final GamificationService gamificationService;

    @GetMapping("/streak")
    public ResponseEntity<StreakResponse> getStreak() {
        return ResponseEntity.ok(
                gamificationService.getStreakAndBadges());
    }
}

