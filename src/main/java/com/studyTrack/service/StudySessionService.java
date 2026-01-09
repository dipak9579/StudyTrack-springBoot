package com.studyTrack.service;

import com.studyTrack.dto.StartSessionRequest;
import com.studyTrack.dto.StudySessionResponse;
import com.studyTrack.entity.StudySession;
import com.studyTrack.entity.User;

public interface StudySessionService {

    StudySessionResponse startSession(StartSessionRequest request);

    StudySessionResponse pauseSession();

    StudySessionResponse stopSession();
    StudySessionResponse resumeSession();

    StudySession getActiveSessionForUser(User user);

    StudySessionResponse getActiveSessionResponse();
}


