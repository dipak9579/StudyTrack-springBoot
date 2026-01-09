package com.studyTrack.service.impl;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studyTrack.dto.LoginRequest;
import com.studyTrack.dto.RegisterRequest;
import com.studyTrack.entity.Role;
import com.studyTrack.entity.User;
import com.studyTrack.repository.UserRepository;
import com.studyTrack.security.JwtUtil;
import com.studyTrack.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	@Override
	public void register(RegisterRequest request) {
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already registered");
		}
		
		User user=User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.STUDENT)
				.build();
		
		userRepository.save(user);
	}

	@Override
	public String login(LoginRequest request) {
		User user=userRepository.findByEmail(request.getEmail())
				.orElseThrow(()->new RuntimeException("Invalid Credentials"));
		
		if(!passwordEncoder.matches(request.getPassword(),user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		return jwtUtil.generateToken(user.getEmail());
	}

}
