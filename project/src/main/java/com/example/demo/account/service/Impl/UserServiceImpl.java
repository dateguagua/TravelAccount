package com.example.demo.account.service.Impl;

import java.io.Console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.mapper.UsersMapper;
import com.example.demo.account.model.dto.UsersDTO;
import com.example.demo.account.model.entity.Users;
import com.example.demo.account.repository.UserRepository;
import com.example.demo.account.service.UserService;
import com.example.demo.account.util.HashUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Override
	public UsersDTO getUser(String username) {
		Users users = userRepository.getUsers(username);
		if(username == null) {
			return null ;
		}
		return usersMapper.toDto(users);
	}


	@Override
	public void LoginUser(String name, String password, String authCode, String sessionAuthCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUser(String username, String password, String email) {
		String salt = HashUtil.getSalt();
		String passwordHash = HashUtil.getHash(password, salt) ;
		Users users = new Users(null, username, passwordHash, salt, email, null);
		userRepository.save(users);
		
	}

	@Override
	public void emailConfirmOk(String username) {
		if(username == null) {
			return;
		}
		userRegisterDAO.emailConfirmOK(username);
	}

}
