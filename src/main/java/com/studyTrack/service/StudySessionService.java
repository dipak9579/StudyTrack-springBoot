package com.studyTrack.service;

import com.studyTrack.dto.StartSessionRequest;
import com.studyTrack.dto.StudySessionResponse;

public interface StudySessionService {

    StudySessionResponse startSession(StartSessionRequest request);

    StudySessionResponse pauseSession();

    StudySessionResponse stopSession();
}

