package com.example.demo.account.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.account.model.dto.CountryPlanDTO;
import com.example.demo.account.model.entity.CountryPlan;

@Component
public class CountryPlanMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public CountryPlanDTO toDto(CountryPlan countryPlan) {
		CountryPlanDTO dto = new CountryPlanDTO();
		dto.setCountryPlanId(countryPlan.getCountryPlanId());
		dto.setUserName(countryPlan.getUser().getUserName());
		dto.setCountryName(countryPlan.getCountryList().getCountryName());
		dto.setStartTime(countryPlan.getStartTime());
		dto.setTotalDays(countryPlan.getTotalDays());
		return dto;
	}
	
	public CountryPlan toEntity(CountryPlanDTO countryPlanDTO) {
		return modelMapper.map(countryPlanDTO, CountryPlan.class);
	}
}
