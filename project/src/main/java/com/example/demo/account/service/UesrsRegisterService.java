package com.example.demo.account.service;

public interface UesrsRegisterService {

	
	void addUser(String username, String password, String email);
	void emailConfirmOk(String username);
}
