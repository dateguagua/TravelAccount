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
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "category_id")
	private Integer categoryId;
	
	@Column(name = "dollar")
	private Double dollar;
	
	@Column(name = "money_date")
	private LocalDateTime moneyDate;
	
	
	
}
