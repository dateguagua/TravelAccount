package com.example.demo.account.model.dto;

import com.example.demo.account.model.entity.Users;

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
public class UsersDTO {
	private Integer userId;
	private String userName;
	private String email;
	private Boolean complete;
}
