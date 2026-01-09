package com.studyTrack.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyTrack.entity.StudyPlan;
import com.studyTrack.entity.StudyPlanStatus;

public interface StudyPlanRepository extends JpaRepository<StudyPlan, Long>{

	List<StudyPlan>findByUserIdAndStudyDate(Long userId,LocalDate studyDate);
	List<StudyPlan>findByUserIdAndStatus(Long userId,StudyPlanStatus status);
}
