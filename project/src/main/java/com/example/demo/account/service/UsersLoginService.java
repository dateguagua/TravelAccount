package com.example.demo.account.service;

import com.example.demo.account.model.dto.UsersDTO;

public interface UsersLoginService {

	UsersDTO login(String name, String password, String authCode, String sessionAuthCode);
}
