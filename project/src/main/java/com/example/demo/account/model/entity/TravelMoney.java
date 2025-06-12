package com.example.demo.account.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "travel_money")
public class TravelMoney {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "money_id")
	private Integer moneyId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "countryplan_id")  
	private CountryPlan countryPlan;
	
	@Column(name = "product_name")
	private String productName;
	
	@ManyToOne(fetch = FetchType.EAGER)  // 不要加 cascade
	@JoinColumn(name = "category_id")
	private Category category;
	
	
	@Column(name = "dollar")
	private Double dollar;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "money_date")
	private LocalDate moneyDate;
	
	
	
}
