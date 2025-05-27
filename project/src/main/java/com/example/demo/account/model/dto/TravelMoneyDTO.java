package com.example.demo.account.model.dto;

import java.time.LocalDateTime;

import com.example.demo.account.model.entity.TravelMoney;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Travel_money")
public class TravelMoneyDTO {
	private Integer moneyId;
	//private Integer userId;
	private String productName;
	private Integer categoryId;
	private Double dollar;
	private LocalDateTime moneyDate;
}
