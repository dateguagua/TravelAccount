package com.example.demo.account.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.account.model.dto.CountryPlanDTO;
import com.example.demo.account.model.dto.TravelMoneyDTO;
import com.example.demo.account.model.entity.CountryPlan;

@Component
public class CountryPlanMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public CountryPlanDTO toDto(CountryPlan countryPlan) {
		CountryPlanDTO dto = new CountryPlanDTO();
		
		dto.setCountryPlanId(countryPlan.getCountryPlanId());
		
		if(countryPlan.getUser() != null) {
			dto.setUserId(countryPlan.getUser().getUserId());
			dto.setUserName(countryPlan.getUser().getUserName());
		}
		
		if(countryPlan.getCountryList() !=null) {
			dto.setCountryId(countryPlan.getCountryList().getCountryId());
			dto.setCountryName(countryPlan.getCountryList().getCountryName());
		}
		
		dto.setStartTime(countryPlan.getStartTime());
		dto.setTotalDays(countryPlan.getTotalDays());
		return dto;
		
		//return modelMapper.map(countryPlan, CountryPlanDTO.class);
	}
	
	public CountryPlan toEntity(CountryPlanDTO countryPlanDTO) {
		return modelMapper.map(countryPlanDTO, CountryPlan.class);
	}
	
	public CountryPlan toEntityWithoutRelations(CountryPlanDTO countryPlanDTO)
	{
		CountryPlan countryPlan = new CountryPlan();
		
		countryPlan.setCountryPlanId(countryPlanDTO.getCountryPlanId());
		countryPlan.setStartTime(countryPlanDTO.getStartTime());
		countryPlan.setTotalDays(countryPlanDTO.getTotalDays());
		
		return countryPlan;
	}
}
