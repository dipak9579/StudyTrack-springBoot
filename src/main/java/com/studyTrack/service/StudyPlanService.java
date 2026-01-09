package com.studyTrack.service;

import java.util.List;

import com.studyTrack.dto.StudyPlanRequest;
import com.studyTrack.dto.StudyPlanResponse;

public interface StudyPlanService {
	
	StudyPlanResponse createTodayPlan(StudyPlanRequest request);
	List<StudyPlanResponse>getTodayPlans();
	StudyPlanResponse markPlanCompleted(Long planId);

}
