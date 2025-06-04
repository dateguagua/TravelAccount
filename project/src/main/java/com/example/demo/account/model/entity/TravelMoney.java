package com.example.demo.account.model.entity;

import java.time.LocalDateTime;

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
	@JoinColumn(name = "user_id")  // 對應到 users 表
	private Users user;
	
	@Column(name = "product_name")
	private String productName;
	
	@ManyToOne(fetch = FetchType.LAZY)  // 不要加 cascade
	@JoinColumn(name = "category_id")
	private Category category;
	
	
	@Column(name = "dollar")
	private Double dollar;
	
	@Column(name = "money_date")
	private LocalDateTime moneyDate;
	
	
	
}
