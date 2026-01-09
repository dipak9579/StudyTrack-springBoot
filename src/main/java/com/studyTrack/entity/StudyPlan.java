package com.studyTrack.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="study_plans",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_id","studyDate","subject"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyPlan {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String subject;
	
	private String topic;
	
	private Integer expectedMinutes;
	
	private LocalDate studyDate;
	
	@Enumerated(EnumType.STRING)
	private StudyPlanStatus status;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=false)
	private User user;

}
