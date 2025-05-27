package com.example.demo.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.account.model.dto.AccountDTO;
import com.example.demo.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping
	public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO)
	{
		AccountDTO saveAccountDTO = accountService.createCount(accountDTO);
		return new ResponseEntity<>(saveAccountDTO, HttpStatus.CREATED);
	}
	

}
