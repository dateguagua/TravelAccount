package com.example.demo.account.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

	private Long id;
	private String name;
	private Boolean expense;
	private String category;
	private Long amount;
	private LocalDateTime createDate;
}
