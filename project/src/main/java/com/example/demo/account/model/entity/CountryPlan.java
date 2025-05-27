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

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Country_Plan")
public class CountryPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "countryPlan_id")
	private Integer countryPlanId;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "country_id")
	private Integer countryId;
	
	@Column(name = "total_days")
	private Integer totalDays;
	
	@Column(name = "start_time")
	private LocalDateTime startTime;
}
