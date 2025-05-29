package com.example.demo.account.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import com.example.demo.account.model.dto.JourneyDTO;
import com.example.demo.account.model.entity.Journey;

@Component
public class JourneyMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public JourneyDTO toDto(Journey journey) {
		return modelMapper.map(journey, JourneyDTO.class);
	}
	
	public Journey toEntity(JourneyDTO journeyDTO) {
		return modelMapper.map(journeyDTO, Journey.class);
	}
}
