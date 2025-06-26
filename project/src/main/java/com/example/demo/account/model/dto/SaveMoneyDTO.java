package com.example.demo.account.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveMoneyDTO {

	private Integer saveMoneyId;
	private String saveMoneyMemo;
	private Double saveMoneyGoal;
	private LocalDate saveMoneyDate;
}
