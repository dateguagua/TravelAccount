package com.example.demo.account.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UsersDTO {
	
	private Integer userId;
	private String userName;
	private String email;
	private Boolean complete;
}
