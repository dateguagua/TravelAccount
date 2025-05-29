package com.example.demo.account.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.mapper.CountryPlanMapper;
import com.example.demo.account.model.dto.CountryPlanDTO;
import com.example.demo.account.repository.CountryPlanRepository;
import com.example.demo.account.service.CountryPlanService;

@Service
public class CountryPlanServiceImpl implements CountryPlanService{

	@Autowired
	private CountryPlanRepository countryPlanRepository;
	
	@Autowired
	private CountryPlanMapper countryPlanMapper;
	
	@Override
	public List<CountryPlanDTO> findAllCountryPlan() {
		return countryPlanRepository.findAll()
									.stream()
									.map(countryPlanMapper::toDto)
									.toList();
	}

	@Override
	public CountryPlanDTO getCountryPlanById(Integer countryPlanId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCountryPlan(CountryPlanDTO countryPlanDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCountryPlan(Integer countryPlanId, CountryPlanDTO countryPlanDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCountryPlan(Integer countryPlanId) {
		// TODO Auto-generated method stub
		
	}

}
