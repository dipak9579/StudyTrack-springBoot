package com.studyTrack.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyTrack.entity.StudySession;
import com.studyTrack.entity.StudySessionStatus;

public interface StudySessionRepository
extends JpaRepository<StudySession, Long> {

Optional<StudySession> findByUserIdAndStatus(
    Long userId, StudySessionStatus status);

List<StudySession> findByUserIdAndStartTimeBetween(
    Long userId,
    LocalDateTime start,
    LocalDateTime end);

Optional<StudySession> findFirstByUserIdAndStatusIn(
	    Long userId,
	    List<StudySessionStatus> statuses
	);



}

