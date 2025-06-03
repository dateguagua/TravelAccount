package com.example.demo.account.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.except.countryPlan.CountryPlanAlreadyExistsException;
import com.example.demo.account.except.countryPlan.CountryPlanNotFoundException;
import com.example.demo.account.mapper.CountryPlanMapper;
import com.example.demo.account.model.dto.CountryPlanDTO;
import com.example.demo.account.model.entity.CountryPlan;
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
		CountryPlan countryPlan = countryPlanRepository.findById(countryPlanId)
								.orElseThrow(() -> new CountryPlanNotFoundException("找不到該旅行計劃：CountryPlanId = " + countryPlanId));
		return countryPlanMapper.toDto(countryPlan);
	}

	@Override
	public void addCountryPlan(CountryPlanDTO countryPlanDTO) {
		Optional<CountryPlan> optCountryPlan = countryPlanRepository.findById(countryPlanDTO.getCountryId());
		if(optCountryPlan.isPresent()) {
			throw new CountryPlanAlreadyExistsException("新增失敗：旅行計劃" + countryPlanDTO.getCountryId() + "已存在");
		}
		CountryPlan countryPlan = countryPlanMapper.toEntity(countryPlanDTO);
		countryPlanRepository.save(countryPlan);
		countryPlanRepository.flush();
	}

	@Override
	public void updateCountryPlan(Integer countryPlanId, CountryPlanDTO countryPlanDTO) {
		Optional<CountryPlan> optCountryPlan = countryPlanRepository.findById(countryPlanDTO.getCountryId());
		if(optCountryPlan.isEmpty()) {
			throw new CountryPlanNotFoundException("修改失敗：旅行計劃" + countryPlanDTO.getCountryId() + "不存在");
		}
		
		countryPlanDTO.setCountryId(countryPlanId);
		CountryPlan countryPlan = countryPlanMapper.toEntity(countryPlanDTO);
		countryPlanRepository.saveAndFlush(countryPlan);
	}

	@Override
	public void deleteCountryPlan(Integer countryPlanId) {
		Optional<CountryPlan> optCountryPlan = countryPlanRepository.findById(countryPlanId);
		if(optCountryPlan.isEmpty()) {
			throw new CountryPlanNotFoundException("刪除失敗：旅行計劃" + countryPlanId + "不存在");
		}
		countryPlanRepository.deleteById(countryPlanId);
	}

}
