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
		JourneyDTO dto = new JourneyDTO();
		dto.setJourneyId(journey.getJourneyId());
		dto.setCountryPlanId(journey.getCountryPlan().getCountryPlanId());
		dto.setLocation(journey.getLocation());
		dto.setAttraction(journey.getAttraction());
		dto.setMemo(journey.getMemo());
		dto.setTime(journey.getTime());
		dto.setDays(journey.getDays());
		
		return dto;
	}
	
	public Journey toEntity(JourneyDTO journeyDTO) {
		return modelMapper.map(journeyDTO, Journey.class);
	}
}
