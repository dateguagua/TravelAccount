package com.example.demo.account.model.dto;

import java.time.LocalDateTime;

import com.example.demo.account.model.entity.Category;
import com.example.demo.account.model.entity.Users;

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
	private String category;
	private Double dollar;
	private LocalDateTime moneyDate;
}
