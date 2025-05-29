package com.example.demo.account.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.account.except.countryList.CountryListAlreadyExistException;
import com.example.demo.account.except.countryList.CountryListNotFoundException;
import com.example.demo.account.mapper.CountryListMapper;
import com.example.demo.account.model.dto.CountryListDTO;
import com.example.demo.account.model.entity.CountryList;
import com.example.demo.account.repository.CountryListRepository;
import com.example.demo.account.service.CountryListService;

public class CountryListServiceImpl implements CountryListService{

	@Autowired
	private CountryListRepository countryListRepository;
	
	@Autowired
	private CountryListMapper countryListMapper;
	
	@Override
	public List<CountryListDTO> findAllCountry() {
		
		return countryListRepository.findAll()
									.stream()
									.map(countryListMapper::toDto)
									.toList();
	}

	@Override
	public CountryListDTO getCountryById(Integer countryId) {
		CountryList countryList = countryListRepository.findById(countryId)
								.orElseThrow(() -> new CountryListNotFoundException("找不到這個國家：CountryListId = "+ countryId));
		return countryListMapper.toDto(countryList);
	}

	@Override
	public void addCountryList(CountryListDTO countryListDTO) {
		Optional<CountryList> optCountryList = countryListRepository.findById(countryListDTO.getCountryId());
		if(optCountryList.isPresent()) {
			throw new CountryListAlreadyExistException("新增國家失敗，此國家已存在" + countryListDTO.getCountryId());
		}
	}

	@Override
	public void updateCountryList(Integer countryId, CountryListDTO countryListDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCategory(Integer countryId) {
		// TODO Auto-generated method stub
		
	}

}
