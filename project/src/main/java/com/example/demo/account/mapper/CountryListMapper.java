package com.example.demo.account.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.example.demo.account.model.dto.CountryListDTO;
import com.example.demo.account.model.entity.CountryList;

@Component
public class CountryListMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public CountryListDTO toDto(CountryList countryList) {
		return modelMapper.map(countryList, CountryListDTO.class);
	}
	
	public CountryList toEntity(CountryListDTO countryListDTO) {
		return modelMapper.map(countryListDTO, CountryList.class);
	}
}
