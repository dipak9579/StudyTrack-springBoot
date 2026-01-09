package com.studyTrack.service;

import com.studyTrack.dto.AuthResponse;
import com.studyTrack.dto.LoginRequest;
import com.studyTrack.dto.RegisterRequest;

public interface AuthService {
	void register(RegisterRequest request);
	AuthResponse login(LoginRequest request);

}
