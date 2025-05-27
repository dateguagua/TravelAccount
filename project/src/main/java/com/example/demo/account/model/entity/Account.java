package com.example.demo.account.model.entity;

import java.io.Console;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "expense")
	private Boolean expense;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "amount")
	private Long amount;
	
	@Column(name = "createDate")
	private LocalDateTime createDate;
	

}

