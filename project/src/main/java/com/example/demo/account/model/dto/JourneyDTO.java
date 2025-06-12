package com.example.demo.account.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.account.model.entity.CountryPlan;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class JourneyDTO {


	private Integer journeyId;
	
	private Integer countryPlanId;
	
	private String location;
	private String attraction;
	private String memo;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate time;
	private Integer days;
	//private Boolean isDelete;
}
