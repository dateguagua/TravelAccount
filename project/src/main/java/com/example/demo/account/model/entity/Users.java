package com.example.demo.account.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class Users {

	private Integer userId;
	private String userName;
	private String email;
	private String password;
	private String hashSalt;
	private Boolean complete;
	
	
}
