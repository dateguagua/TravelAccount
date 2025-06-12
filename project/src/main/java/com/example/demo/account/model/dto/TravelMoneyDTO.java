package com.example.demo.account.model.dto;


import java.time.LocalDate;

import com.example.demo.account.model.entity.Category;
import com.example.demo.account.model.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TravelMoneyDTO {
	
	private Integer moneyId;
	private Integer countryPlanId ;
	
	private String productName;
	
	private Integer categoryId;
	private String category;
	
	private Double dollar;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate moneyDate;
}
