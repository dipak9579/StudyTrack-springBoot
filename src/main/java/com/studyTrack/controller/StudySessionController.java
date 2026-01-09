package com.studyTrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyTrack.dto.StartSessionRequest;
import com.studyTrack.dto.StudySessionResponse;
import com.studyTrack.service.StudySessionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/study-sessions")
@RequiredArgsConstructor
public class StudySessionController {

    private final StudySessionService studySessionService;

    @PostMapping("/start")
    public ResponseEntity<StudySessionResponse> start(
            @Valid @RequestBody StartSessionRequest request) {
        return ResponseEntity.ok(
                studySessionService.startSession(request));
    }

    @PostMapping("/pause")
    public ResponseEntity<StudySessionResponse> pause() {
        return ResponseEntity.ok(
                studySessionService.pauseSession());
    }

    @PostMapping("/stop")
    public ResponseEntity<StudySessionResponse> stop() {
        return ResponseEntity.ok(
                studySessionService.stopSession());
    }
}

