package com.example.demo.account.service;

import java.util.List;

import com.example.demo.account.model.dto.JourneyDTO;



public interface JourneyService {

	public List<JourneyDTO> findAllJourney();
	public JourneyDTO getJourneyById(Integer journeyId);
	
	public void addJourney(JourneyDTO journeyDTO);
	public void updateJourney(Integer journeyId, JourneyDTO journeyDTO);
	public void deleteJourney(Integer journeyId);
}
