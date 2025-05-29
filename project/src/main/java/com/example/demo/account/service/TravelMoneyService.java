package com.example.demo.account.service;

import java.util.List;

import com.example.demo.account.model.dto.TravelMoneyDTO;



public interface TravelMoneyService {

	public List<TravelMoneyDTO> findAllTravelMoney();
	public TravelMoneyDTO getTravelMoneyById(Integer moneyId);
	
	public void addTravelMoney(TravelMoneyDTO travelMoneyDTO);
	public void updateTravelMoney(Integer moneyId, TravelMoneyDTO travelMoneyDTO);
	public void deleteTravelMoney(Integer moneyId);
}
