package com.example.demo.account.service;

import java.util.List;

import com.example.demo.account.model.dto.CountryListDTO;

public interface CountryListService {

	public List<CountryListDTO> findAllCountry();
	public CountryListDTO getCountryById(Integer countryId);
	
	public void addCountryList(CountryListDTO countryListDTO);
	public void updateCountryList(Integer countryId, CountryListDTO countryListDTO);
	public void deleteCategory(Integer countryId);
}
