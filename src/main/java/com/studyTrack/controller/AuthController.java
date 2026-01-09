package com.studyTrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyTrack.dto.AuthResponse;
import com.studyTrack.dto.LoginRequest;
import com.studyTrack.dto.RegisterRequest;
import com.studyTrack.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<String>register(@Valid @RequestBody RegisterRequest request){
		authService.register(request);
		return ResponseEntity.ok("User registered successfully");	
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(
	        @Valid @RequestBody LoginRequest request) {

	    return ResponseEntity.ok(authService.login(request));
	}

}
