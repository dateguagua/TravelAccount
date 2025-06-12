package com.example.demo.account.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.account.model.entity.CountryList;
import com.example.demo.account.model.entity.CountryPlan;
import com.example.demo.account.model.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CountryPlanDTO {

	private Integer countryPlanId;
	
	private Integer userId;
	private String userName;
	
	private Integer countryId;
	private String countryName;
	
	private Integer totalDays;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startTime;
}
