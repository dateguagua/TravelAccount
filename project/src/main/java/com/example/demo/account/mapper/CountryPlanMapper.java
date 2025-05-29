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
		return modelMapper.map(countryPlan, CountryPlanDTO.class);
	}
	
	public CountryPlan toEntity(CountryPlanDTO countryPlanDTO) {
		return modelMapper.map(countryPlanDTO, CountryPlan.class);
	}
}
