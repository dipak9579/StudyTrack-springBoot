package com.studyTrack.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.studyTrack.dto.StartSessionRequest;
import com.studyTrack.dto.StudySessionResponse;
import com.studyTrack.entity.StudySession;
import com.studyTrack.entity.StudySessionStatus;
import com.studyTrack.entity.User;
import com.studyTrack.exception.BadRequestException;
import com.studyTrack.exception.ResourceNotFoundException;
import com.studyTrack.repository.StudySessionRepository;
import com.studyTrack.repository.UserRepository;
import com.studyTrack.service.StudySessionService;
import com.studyTrack.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudySessionServiceImpl implements StudySessionService {

    private final StudySessionRepository sessionRepository;
    private final UserRepository userRepository;

    // ===================== USER =====================
    private User getCurrentUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    // ===================== UNFINISHED SESSION =====================
    @Override
    public StudySession getActiveSessionForUser(User user) {
        return sessionRepository
                .findFirstByUserIdAndStatusIn(
                        user.getId(),
                        List.of(
                                StudySessionStatus.RUNNING,
                                StudySessionStatus.PAUSED
                        )
                )
                .orElse(null);
    }

    // ===================== START =====================
    @Override
    public StudySessionResponse startSession(
            StartSessionRequest request) {

        User user = getCurrentUser();

        if (getActiveSessionForUser(user) != null) {
            throw new RuntimeException(
                    "Active study session already exists");
        }

        StudySession session = StudySession.builder()
                .subject(request.getSubject())
                .startTime(LocalDateTime.now())
                .status(StudySessionStatus.RUNNING)
                .user(user)
                .build();

        sessionRepository.save(session);
        return mapToResponse(session);
    }

    // ===================== PAUSE =====================
    @Override
    public StudySessionResponse pauseSession() {

        User user = getCurrentUser();
        StudySession session = getActiveSessionForUser(user);

        if (session == null || session.getStatus() != StudySessionStatus.RUNNING) {
            throw new RuntimeException("No running session to pause");
        }


        session.setStatus(StudySessionStatus.PAUSED);
        sessionRepository.save(session);

        return mapToResponse(session);
    }

    // ===================== STOP =====================
    @Override
    public StudySessionResponse stopSession() {

        User user = getCurrentUser();
        StudySession session = getActiveSessionForUser(user);

        if (session == null) {
            throw new RuntimeException("No active session");
        }

        session.setEndTime(LocalDateTime.now());

        long minutes = Duration.between(
                session.getStartTime(),
                session.getEndTime()
        ).toMinutes();

        if (minutes < 5) {
            throw new BadRequestException(
                "Study session must be at least 5 minutes");
        }


        session.setTotalMinutes(minutes);
        session.setStatus(StudySessionStatus.COMPLETED);

        sessionRepository.save(session);
        return mapToResponse(session);
    }

    // ===================== ACTIVE SESSION RESPONSE =====================
    @Override
    public StudySessionResponse getActiveSessionResponse() {

        User user = getCurrentUser();
        StudySession session = getActiveSessionForUser(user);

        if (session == null) {
            return null;
        }

        long elapsedSeconds = Duration.between(
                session.getStartTime(),
                LocalDateTime.now()
        ).getSeconds();

        return StudySessionResponse.builder()
                .id(session.getId())
                .subject(session.getSubject())
                .status(session.getStatus()) // RUNNING or PAUSED
                .elapsedSeconds(elapsedSeconds)
                .build();
    }

    // ===================== MAPPER =====================
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
    
    @Override
    public StudySessionResponse resumeSession() {

        User user = getCurrentUser();
        StudySession session = getActiveSessionForUser(user);

        if (session == null || session.getStatus() != StudySessionStatus.PAUSED) {
            throw new RuntimeException("No paused session to resume");
        }

        session.setStatus(StudySessionStatus.RUNNING);
        sessionRepository.save(session);

        return mapToResponse(session);
    }

}
