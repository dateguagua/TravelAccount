package com.example.demo.account.service.Impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.except.category.CategoryNotFoundException;
import com.example.demo.account.except.countryPlan.CountryPlanNotFoundException;
import com.example.demo.account.except.money.MoneyAlreadyExistException;
import com.example.demo.account.except.money.MoneyNotFoundException;
import com.example.demo.account.mapper.TravelMoneyMapper;
import com.example.demo.account.model.dto.TravelMoneyDTO;
import com.example.demo.account.model.entity.Category;
import com.example.demo.account.model.entity.CountryPlan;
import com.example.demo.account.model.entity.TravelMoney;
import com.example.demo.account.repository.CategoryRepository;
import com.example.demo.account.repository.CountryListRepository;
import com.example.demo.account.repository.CountryPlanRepository;
import com.example.demo.account.repository.TravelMoneyRepository;
import com.example.demo.account.service.TravelMoneyService;

@Service
public class TravelMoneyServiceImpl implements TravelMoneyService{

	@Autowired
	private TravelMoneyRepository travelMoneyRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CountryPlanRepository countryPlanRepository;
	
	@Autowired 
	private TravelMoneyMapper travelMoneyMapper;
	
	@Override
	public List<TravelMoneyDTO> findAllTravelMoney() {
		
		return travelMoneyRepository.findAll()
									.stream()
									.map(travelMoneyMapper::toDto)
									.toList();
	}

	@Override
	public TravelMoneyDTO getTravelMoneyById(Integer moneyId) {
		TravelMoney travelMoney = travelMoneyRepository.findById(moneyId)
									.orElseThrow(() -> new MoneyNotFoundException("找不到該筆消費：moneyId="+ moneyId));
		return travelMoneyMapper.toDto(travelMoney);
	}

	@Override
	public void addTravelMoney(TravelMoneyDTO travelMoneyDTO) {
		//Optional<TravelMoney> optTravelMoney = travelMoneyRepository.findById(travelMoneyDTO.getMoneyId());
		if(travelMoneyRepository.existsByMoneyId(travelMoneyDTO.getMoneyId())) {
			throw new MoneyAlreadyExistException("新增失敗"+travelMoneyDTO.getMoneyId()+"帳目已存在");
		}
		
		Category category = categoryRepository.findById(travelMoneyDTO.getCategoryId())
				 .orElseThrow(() -> new CategoryNotFoundException("找不到該帳目" + travelMoneyDTO.getCategoryId()));
		
		CountryPlan countryPlan = countryPlanRepository.findById(travelMoneyDTO.getCountryPlanId())
			    .orElseThrow(() -> new CountryPlanNotFoundException("找不到該旅行計畫：" + travelMoneyDTO.getCountryPlanId()));

		
		TravelMoney travelMoney = travelMoneyMapper.toEntityWithoutRelations(travelMoneyDTO);
		travelMoney.setCategory(category);
		travelMoney.setCountryPlan(countryPlan);
		
		travelMoneyRepository.save(travelMoney);
		travelMoneyRepository.flush();
		
	}

	@Override
	public void updateTravelMoney(Integer moneyId, TravelMoneyDTO travelMoneyDTO) {
		//Optional<TravelMoney> optTravelMoney = travelMoneyRepository.findById(travelMoneyDTO.getMoneyId());
		
		TravelMoney existing = travelMoneyRepository.findById(moneyId)
							.orElseThrow(() -> new MoneyNotFoundException("修改失敗"+ moneyId+"帳目不存在")); 
		
		Category category = categoryRepository.findById(travelMoneyDTO.getCategoryId())
						 .orElseThrow(() -> new CategoryNotFoundException("找不到該帳目" + travelMoneyDTO.getCategoryId()));
		
		CountryPlan countryPlan = countryPlanRepository.findById(travelMoneyDTO.getCountryPlanId())
			    .orElseThrow(() -> new CountryPlanNotFoundException("找不到該旅行計畫：" + travelMoneyDTO.getCountryPlanId()));
		
		travelMoneyDTO.setMoneyId(moneyId);
		TravelMoney travelMoney = travelMoneyMapper.toEntityWithoutRelations(travelMoneyDTO);
		
		travelMoney.setCategory(category);
		travelMoney.setCountryPlan(countryPlan);
		
		travelMoneyRepository.saveAndFlush(travelMoney);
	}

	@Override
	public void deleteTravelMoney(Integer moneyId) {
		Optional<TravelMoney> optTravelMoney = travelMoneyRepository.findById(moneyId);
		if(optTravelMoney.isEmpty()) {
			throw new MoneyNotFoundException("刪除失敗"+travelMoneyRepository.findById(moneyId)+"帳目不存在");
		}
		travelMoneyRepository.deleteById(moneyId);
		
	}

}
