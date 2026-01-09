package com.studyTrack.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.studyTrack.dto.StreakResponse;
import com.studyTrack.entity.BadgeType;
import com.studyTrack.entity.StudySession;
import com.studyTrack.entity.StudySessionStatus;
import com.studyTrack.entity.User;
import com.studyTrack.repository.StudySessionRepository;
import com.studyTrack.repository.UserRepository;
import com.studyTrack.service.GamificationService;
import com.studyTrack.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GamificationServiceImpl
        implements GamificationService {

    private final StudySessionRepository sessionRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public StreakResponse getStreakAndBadges() {

        User user = getCurrentUser();
        int streak = calculateStreak(user);
        List<BadgeType> badges = calculateBadges(user, streak);

        return StreakResponse.builder()
                .currentStreak(streak)
                .earnedBadges(badges)
                .build();
    }

    private int calculateStreak(User user) {

        int streak = 0;

        for (int i = 0; ; i++) {
            LocalDate date = LocalDate.now().minusDays(i);

            long minutes = getTotalStudyMinutes(user, date);

            if (minutes >= 30) {
                streak++;
            } else {
                break;
            }
        }
        return streak;
    }

    private long getTotalStudyMinutes(User user, LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        return sessionRepository
            .findByUserIdAndStartTimeBetween(
                    user.getId(), start, end)
            .stream()
            .filter(s -> s.getStatus() == StudySessionStatus.COMPLETED) // 🔥
            .filter(s -> s.getTotalMinutes() != null)
            .mapToLong(StudySession::getTotalMinutes)
            .sum();
    }



    private List<BadgeType> calculateBadges(
            User user, int streak) {

        List<BadgeType> badges = new ArrayList<>();

        if (streak >= 1) {
            badges.add(BadgeType.FIRST_DAY);
        }
        if (streak >= 3) {
            badges.add(BadgeType.THREE_DAY_STREAK);
        }
        if (streak >= 7) {
            badges.add(BadgeType.SEVEN_DAY_STREAK);
        }

        long totalHours =
        	    sessionRepository.findByUserIdAndStartTimeBetween(
        	            user.getId(),
        	            LocalDate.now().minusYears(1).atStartOfDay(),
        	            LocalDateTime.now())
        	        .stream()
        	        .filter(s -> s.getStatus() == StudySessionStatus.COMPLETED) // 🔥
        	        .filter(s -> s.getTotalMinutes() != null)
        	        .mapToLong(StudySession::getTotalMinutes)
        	        .sum() / 60;


        if (totalHours >= 30) {
            badges.add(BadgeType.THIRTY_HOURS_STUDY);
        }

        return badges;
    }
}
