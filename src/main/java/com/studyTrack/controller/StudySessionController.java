package com.studyTrack.controller;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyTrack.dto.StartSessionRequest;
import com.studyTrack.dto.StudySessionResponse;
import com.studyTrack.entity.StudySession;
import com.studyTrack.entity.User;
import com.studyTrack.service.StudySessionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/study-sessions")
@RequiredArgsConstructor
public class StudySessionController {

    private final StudySessionService studySessionService;
   
    @GetMapping("/active")
    public ResponseEntity<StudySessionResponse> getActiveSession() {

        StudySessionResponse response =
                studySessionService.getActiveSessionResponse();

        if (response == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }



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
    
    @PostMapping("/resume")
    public ResponseEntity<StudySessionResponse> resume() {
        return ResponseEntity.ok(
                studySessionService.resumeSession());
    }

}

