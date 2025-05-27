package com.example.demo.account.model.dto;

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
public class CountryPlanDTO {

	private Integer countryPlanId;
	//private Integer countryId;
	private Integer totalDays;
	private LocalDateTime startTime;
}
