package com.example.demo.account.service;

import com.example.demo.account.model.dto.UsersDTO;

public interface UserService {

	public UsersDTO getUser(String username);
	public void addUser(String username, String password, String email);
	public void emailConfirmOk(String username);
	public void LoginUser(String name, String password, String authCode, String sessionAuthCode);
}
