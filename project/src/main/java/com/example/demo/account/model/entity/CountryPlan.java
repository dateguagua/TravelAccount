package com.example.demo.account.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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

	private Integer countryPlanId;
	private Integer userId;
	private Integer countryId;
	private Integer totalDays;
	private LocalDateTime startTime;
}
