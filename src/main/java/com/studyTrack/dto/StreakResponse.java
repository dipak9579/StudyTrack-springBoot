package com.studyTrack.dto;

import java.util.List;

import com.studyTrack.entity.BadgeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StreakResponse {

    private int currentStreak;
    private List<BadgeType> earnedBadges;
}

