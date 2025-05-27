package com.example.demo.account.service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.model.dto.AccountDTO;
import com.example.demo.account.model.entity.Account;
import com.example.demo.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public AccountDTO createCount(AccountDTO accountDTO) {
		
		Account account = modelMapper.map(accountDTO, Account.class);
		
		if(account.getCreateDate() == null) {
			ZonedDateTime utcPlus8 = ZonedDateTime.now(ZoneId.of("Asia/Taipei"));
			LocalDateTime currentDateTime = utcPlus8.toLocalDateTime();
		//account.setCreateDate(currentDateTime);
		}
		Account savedAccount = accountRepository.save(account);
		return modelMapper.map(savedAccount, AccountDTO.class);
	}

}
