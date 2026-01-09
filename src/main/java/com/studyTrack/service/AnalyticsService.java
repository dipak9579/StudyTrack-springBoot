package com.studyTrack.service;

import java.util.List;

import com.studyTrack.dto.DailyProgressResponse;

public interface AnalyticsService {

    DailyProgressResponse getTodayProgress();

    List<DailyProgressResponse> getWeeklyProgress();
}
