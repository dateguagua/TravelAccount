package com.example.demo.account.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.except.users.CertException;
import com.example.demo.account.except.users.PasswordException;
import com.example.demo.account.except.users.UserNotFoundException;
import com.example.demo.account.model.dto.UsersCert;
import com.example.demo.account.model.entity.Users;
import com.example.demo.account.repository.UserRepository;
import com.example.demo.account.service.CertService;

@Service
public class CertServiceImpl implements CertService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UsersCert getCert(String username, String password) throws UserNotFoundException, PasswordException
	{
		// 1. 是否有此人
		Users users = userRepository.getUsers(username);
		if(username == null) {
			throw new UserNotFoundException("查無此人");
		}
		
		// 2. 密碼 hash 比對
		String passwordHash = Hash.getHash(password, users.getHashSalt());
	}

}
