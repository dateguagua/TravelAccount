package com.example.demo.account.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




import com.example.demo.account.model.dto.TravelMoneyDTO;
import com.example.demo.account.model.dto.UsersDTO;
import com.example.demo.account.model.entity.TravelMoney;
import com.example.demo.account.model.entity.Users;

@Component
public class UsersMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public UsersDTO toDto(Users users) {
		return modelMapper.map(users, UsersDTO.class);
	}
	
	public Users toEntity(UsersDTO usersDTO) {
		return modelMapper.map(usersDTO, Users.class);
	}
}
