package com.studyTrack.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyTrack.dto.DailyProgressResponse;
import com.studyTrack.service.AnalyticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/today")
    public ResponseEntity<DailyProgressResponse> today() {
        return ResponseEntity.ok(
                analyticsService.getTodayProgress());
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<DailyProgressResponse>> weekly() {
        return ResponseEntity.ok(
                analyticsService.getWeeklyProgress());
    }
}
