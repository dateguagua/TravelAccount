package com.example.demo.account.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import com.example.demo.account.model.dto.JourneyDTO;
import com.example.demo.account.model.dto.TravelMoneyDTO;
import com.example.demo.account.model.entity.Journey;
import com.example.demo.account.model.entity.TravelMoney;

@Component
public class TravelMoneyMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public TravelMoneyDTO toDto(TravelMoney travelMoney) {
		return modelMapper.map(travelMoney, TravelMoneyDTO.class);
	}
	
	public TravelMoney toEntity(TravelMoneyDTO travelMoneyDTO) {
		return modelMapper.map(travelMoneyDTO, TravelMoney.class);
	}
}
