package com.example.demo.account.model.dto;

import java.time.LocalDateTime;

import com.example.demo.account.model.entity.CountryPlan;
import com.example.demo.account.model.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CountryPlanDTO {

	private Integer countryPlanId;
	private Users users;
	private Integer countryId;
	private Integer totalDays;
	private LocalDateTime startTime;
}
