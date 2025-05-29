package com.example.demo.account.service;

import java.util.List;

import com.example.demo.account.model.dto.CountryPlanDTO;



public interface CountryPlanService {

	public List<CountryPlanDTO> findAllCountryPlan();
	public CountryPlanDTO getCountryPlanById(Integer countryPlanId);
	
	public void addCountryPlan(CountryPlanDTO countryPlanDTO);
	public void updateCountryPlan(Integer countryPlanId, CountryPlanDTO countryPlanDTO);
	public void deleteCountryPlan(Integer countryPlanId);
}
