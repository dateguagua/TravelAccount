package com.example.demo.account.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.except.money.MoneyAlreadyExistException;
import com.example.demo.account.except.money.MoneyNotFoundException;
import com.example.demo.account.mapper.TravelMoneyMapper;
import com.example.demo.account.model.dto.TravelMoneyDTO;
import com.example.demo.account.model.entity.TravelMoney;

import com.example.demo.account.repository.TravelMoneyRepository;
import com.example.demo.account.service.TravelMoneyService;

@Service
public class TravelMoneyServiceImpl implements TravelMoneyService{

	@Autowired
	private TravelMoneyRepository travelMoneyRepository;
	
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
		Optional<TravelMoney> optTravelMoney = travelMoneyRepository.findById(travelMoneyDTO.getMoneyId());
		if(optTravelMoney.isPresent()) {
			throw new MoneyAlreadyExistException("新增失敗"+travelMoneyDTO.getMoneyId()+"帳目已存在");
		}
		TravelMoney travelMoney = travelMoneyMapper.toEntity(travelMoneyDTO);
		travelMoneyRepository.save(travelMoney);
		travelMoneyRepository.flush();
		
	}

	@Override
	public void updateTravelMoney(Integer moneyId, TravelMoneyDTO travelMoneyDTO) {
		Optional<TravelMoney> optTravelMoney = travelMoneyRepository.findById(travelMoneyDTO.getMoneyId());
		if(optTravelMoney.isEmpty()) {
			throw new MoneyNotFoundException("修改失敗"+travelMoneyDTO.getMoneyId()+"帳目不存在");
		}
		travelMoneyDTO.setCategoryId(moneyId);
		TravelMoney travelMoney = travelMoneyMapper.toEntity(travelMoneyDTO);
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
