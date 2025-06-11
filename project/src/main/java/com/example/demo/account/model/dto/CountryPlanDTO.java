package com.example.demo.account.model.dto;

import java.time.LocalDateTime;

import com.example.demo.account.model.entity.CountryList;
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
	private String userName;
	private String countryName;
	private Integer totalDays;
	private LocalDateTime startTime;
}
