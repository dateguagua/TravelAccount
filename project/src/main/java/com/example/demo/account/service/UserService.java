package com.example.demo.account.service;

import java.util.List;

import com.example.demo.account.model.dto.UsersDTO;

public interface UserService {

	public List<UsersDTO> findAllUser();
	public UsersDTO getUser(String username);
	public void addUser(String username, String password, String email);
}
