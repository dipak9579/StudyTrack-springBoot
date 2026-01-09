package com.studyTrack.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.studyTrack.dto.StartSessionRequest;
import com.studyTrack.dto.StudySessionResponse;
import com.studyTrack.entity.StudySession;
import com.studyTrack.entity.StudySessionStatus;
import com.studyTrack.entity.User;
import com.studyTrack.exception.ResourceNotFoundException;
import com.studyTrack.repository.StudySessionRepository;
import com.studyTrack.repository.UserRepository;
import com.studyTrack.service.StudySessionService;
import com.studyTrack.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudySessionServiceImpl
        implements StudySessionService {

    private final StudySessionRepository sessionRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    private StudySession getActiveSession(User user) {
        return sessionRepository
                .findByUserIdAndStatus(
                        user.getId(),
                        StudySessionStatus.RUNNING)
                .orElse(null);
    }

    @Override
    public StudySessionResponse startSession(
            StartSessionRequest request) {

        User user = getCurrentUser();

        if (getActiveSession(user) != null) {
            throw new RuntimeException(
                    "Active study session already running");
        }

        StudySession session = StudySession.builder()
                .subject(request.getSubject())
                .startTime(LocalDateTime.now())
                .status(StudySessionStatus.RUNNING)
                .user(user)
                .build();

        return mapToResponse(
                sessionRepository.save(session));
    }

    @Override
    public StudySessionResponse pauseSession() {

        User user = getCurrentUser();

        StudySession session = getActiveSession(user);
        if (session == null) {
            throw new RuntimeException("No active session");
        }

        session.setStatus(StudySessionStatus.PAUSED);
        return mapToResponse(
                sessionRepository.save(session));
    }

    @Override
    public StudySessionResponse stopSession() {

        User user = getCurrentUser();

        StudySession session = getActiveSession(user);
        if (session == null) {
            throw new RuntimeException("No active session");
        }

        session.setEndTime(LocalDateTime.now());

        long minutes = Duration.between(
                session.getStartTime(),
                session.getEndTime()).toMinutes();

        if (minutes < 5) {
            throw new RuntimeException(
                    "Study session must be at least 5 minutes");
        }

        session.setTotalMinutes(minutes);
        session.setStatus(StudySessionStatus.COMPLETED);

        return mapToResponse(
                sessionRepository.save(session));
    }

    private StudySessionResponse mapToResponse(
            StudySession session) {

        return StudySessionResponse.builder()
                .id(session.getId())
                .subject(session.getSubject())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .totalMinutes(session.getTotalMinutes())
                .status(session.getStatus())
                .build();
    }
}

