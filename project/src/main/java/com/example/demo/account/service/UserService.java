package com.example.demo.account.service;

import com.example.demo.account.model.dto.UsersDTO;

public interface UserService {

	public UsersDTO getUser(String username);
	public void addUser(String username, String password, String email);
}
