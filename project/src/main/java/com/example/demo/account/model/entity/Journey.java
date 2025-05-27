package com.example.demo.account.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "journey")
public class Journey {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "journey_id")
	private Integer journeyId;
	
	@Column(name = "countryPlan_id")
	private Integer countryPlanId;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "attraction")
	private String attraction;
	
	@Column(name = "memo")
	private String memo;
	
	@Column(name = "time")
	private LocalDateTime time;
	
	@Column(name = "days")
	private Integer days;
	
	@Column(name = "isDelete")
	private Boolean isDelete;
	
}
