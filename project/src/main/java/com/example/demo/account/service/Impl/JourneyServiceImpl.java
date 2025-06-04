package com.example.demo.account.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.except.journey.JourneyAlreadyExistException;
import com.example.demo.account.except.journey.JourneyNotFoundException;
import com.example.demo.account.mapper.JourneyMapper;
import com.example.demo.account.model.dto.JourneyDTO;
import com.example.demo.account.model.entity.Journey;
import com.example.demo.account.repository.JourneyRepository;
import com.example.demo.account.service.JourneyService;

@Service
public class JourneyServiceImpl implements JourneyService {

	@Autowired 
	private JourneyRepository journeyRepository;
	
	@Autowired
	private JourneyMapper journeyMapper;
	
	@Override
	public List<JourneyDTO> findAllJourney() {
	
		return journeyRepository.findAll()
						.stream()
						.map(journeyMapper::toDto)
						.toList();
	}

	@Override
	public JourneyDTO getJourneyById(Integer journeyId) {
		Journey journey = journeyRepository.findById(journeyId)
								.orElseThrow(() -> new JourneyNotFoundException("找不到旅遊行程：journeyId = " + journeyId));
		return journeyMapper.toDto(journey);
	}

	@Override
	public void addJourney(JourneyDTO journeyDTO) {
		//Optional<Journey> optJourney = journeyRepository.findById(journeyDTO.getJourneyId());
		if(journeyRepository.existsByJourneyId(journeyDTO.getJourneyId())) {
			throw new JourneyAlreadyExistException("新增失敗" + journeyDTO.getJourneyId()+"行程已存在");
		}
		Journey journey = journeyMapper.toEntity(journeyDTO);
		journeyRepository.save(journey);
		journeyRepository.flush();
	}

	@Override
	public void updateJourney(Integer journeyId, JourneyDTO journeyDTO) {
		Optional<Journey> optJourney = journeyRepository.findById(journeyDTO.getJourneyId());
		if(optJourney.isEmpty()) {
			throw new JourneyNotFoundException("修改失敗" + journeyDTO.getJourneyId()+"行程不存在");
		}
		
		journeyDTO.setJourneyId(journeyId);
		Journey journey = journeyMapper.toEntity(journeyDTO);
		journeyRepository.saveAndFlush(journey);
	}

	@Override
	public void deleteJourney(Integer journeyId) {
		Optional<Journey> optJourney = journeyRepository.findById(journeyId);
		if(optJourney.isEmpty()) {
			throw new JourneyNotFoundException("刪除失敗" + journeyId+"行程不存在");
		}
		journeyRepository.deleteById(journeyId);
		
	}

}
