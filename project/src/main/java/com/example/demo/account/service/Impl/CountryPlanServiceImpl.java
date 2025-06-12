package com.example.demo.account.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.account.except.countryList.CountryListNotFoundException;
import com.example.demo.account.except.countryPlan.CountryPlanAlreadyExistsException;
import com.example.demo.account.except.countryPlan.CountryPlanNotFoundException;
import com.example.demo.account.except.users.UserNotFoundException;
import com.example.demo.account.mapper.CountryPlanMapper;
import com.example.demo.account.model.dto.CountryPlanDTO;
import com.example.demo.account.model.entity.CountryList;
import com.example.demo.account.model.entity.CountryPlan;
import com.example.demo.account.model.entity.Users;
import com.example.demo.account.repository.CountryListRepository;
import com.example.demo.account.repository.CountryPlanRepository;
import com.example.demo.account.repository.UserRepository;
import com.example.demo.account.service.CountryPlanService;

@Service
@Transactional
public class CountryPlanServiceImpl implements CountryPlanService{

	@Autowired
	private CountryPlanRepository countryPlanRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CountryListRepository countryListRepository;
	
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
		//Optional<CountryPlan> optCountryPlan = countryPlanRepository.findById(countryPlanDTO.getCountryId());
		if(countryPlanRepository.existsByCountryPlanId(countryPlanDTO.getCountryPlanId())) {
			throw new CountryPlanAlreadyExistsException("新增失敗：旅行計劃" + countryPlanDTO.getCountryPlanId() + "已存在");
		}
		
		Users users = userRepository.findById(countryPlanDTO.getUserId())
					.orElseThrow(() -> new UserNotFoundException("找不到使用者" + countryPlanDTO.getUserId()));
		
		CountryList countryList = countryListRepository.findById(countryPlanDTO.getCountryId())
					.orElseThrow(() -> new CountryListNotFoundException("找不到該國家" + countryPlanDTO.getCountryId()));
		
		CountryPlan countryPlan = countryPlanMapper.toEntityWithoutRelations(countryPlanDTO);
		
		countryPlan.setUser(users);
		countryPlan.setCountryList(countryList);
		
		countryPlanRepository.save(countryPlan);
		countryPlanRepository.flush();
	}

	@Override
	public void updateCountryPlan(Integer countryPlanId, CountryPlanDTO countryPlanDTO) {
		
		CountryPlan existing = countryPlanRepository.findById(countryPlanId)
							.orElseThrow(() -> new CountryPlanNotFoundException("修改失敗：旅行計劃" + countryPlanId + "不存在"));
		
		countryPlanDTO.setCountryPlanId(countryPlanId);
		
		Users users = userRepository.findById(countryPlanDTO.getUserId())
				.orElseThrow(() -> new UserNotFoundException("找不到使用者" + countryPlanDTO.getUserId()));
		
		CountryList countryList = countryListRepository.findById(countryPlanDTO.getCountryId())
				.orElseThrow(() -> new CountryListNotFoundException("找不到該國家" + countryPlanDTO.getCountryId()));
		
		CountryPlan countryPlan = countryPlanMapper.toEntityWithoutRelations(countryPlanDTO);
		countryPlan.setUser(users);
		countryPlan.setCountryList(countryList);
		
		countryPlanRepository.saveAndFlush(countryPlan);
	}

	@Override
	@Transactional
	public void deleteCountryPlan(Integer countryPlanId) {
		Optional<CountryPlan> optCountryPlan = countryPlanRepository.findById(countryPlanId);
		if(optCountryPlan.isEmpty()) {
			throw new CountryPlanNotFoundException("刪除失敗：旅行計劃" + countryPlanId + "不存在");
		}
		countryPlanRepository.deleteById(countryPlanId);
	}

}
