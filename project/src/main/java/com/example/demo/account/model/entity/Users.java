package com.example.demo.account.model.entity;


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
@Table(name = "Users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //自動生成ID
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "user_name", unique = true, nullable = false, length = 50)
	private String userName;
	
	@Column(name = "email",unique = true)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "hash_salt")
	private String hashSalt;
	
	@Column(name = "complete")
	private Boolean complete;
	
	
}
