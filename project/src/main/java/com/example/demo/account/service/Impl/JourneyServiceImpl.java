package com.example.demo.account.service.Impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.except.countryPlan.CountryPlanNotFoundException;
import com.example.demo.account.except.journey.JourneyAlreadyExistException;
import com.example.demo.account.except.journey.JourneyException;
import com.example.demo.account.except.journey.JourneyNotFoundException;
import com.example.demo.account.except.journey.JourneyOutOfDateException;
import com.example.demo.account.mapper.JourneyMapper;
import com.example.demo.account.model.dto.CountryPlanDTO;
import com.example.demo.account.model.dto.JourneyDTO;
import com.example.demo.account.model.entity.CountryPlan;
import com.example.demo.account.model.entity.Journey;
import com.example.demo.account.repository.CountryPlanRepository;
import com.example.demo.account.repository.JourneyRepository;
import com.example.demo.account.service.JourneyService;

@Service
public class JourneyServiceImpl implements JourneyService {

	@Autowired 
	private JourneyRepository journeyRepository;
	
	@Autowired
	private JourneyMapper journeyMapper;
	
	@Autowired
	private CountryPlanRepository countryPlanRepository;
	
	private void validateJourneyDate(JourneyDTO journeyDTO, CountryPlan countryPlan) {
	    // 檢查必要欄位是否為 null
	    if (journeyDTO.getTime() == null || countryPlan.getStartTime() == null) {
	        throw new JourneyException("日期欄位不能為空");
	    }
	    
	    if (countryPlan.getTotalDays() == null || journeyDTO.getDays() == null) {
	        throw new JourneyException("天數欄位不能為空");
	    }
	    
	    try {
	        LocalDate journeyDate = journeyDTO.getTime();
	        LocalDate planStartDate = countryPlan.getStartTime();
	        LocalDate planEndDate = planStartDate.plusDays(countryPlan.getTotalDays() - 1);

	        // 驗證日期範圍
	        if (journeyDate.isBefore(planStartDate) || journeyDate.isAfter(planEndDate)) {
	            throw new JourneyException(
	                String.format("行程日期 %s 超出旅遊計劃範圍 (%s 到 %s)",
	                    journeyDate, planStartDate, planEndDate)
	            );
	        }

	        // 驗證天數範圍
	        if (journeyDTO.getDays() < 1 || journeyDTO.getDays() > countryPlan.getTotalDays()) {
	            throw new JourneyException(
	                String.format("行程天數 %d 超出範圍 (1 到 %d)",
	                    journeyDTO.getDays(), countryPlan.getTotalDays())
	            );
	        }
	    } catch (Exception e) {
	        if (e instanceof JourneyOutOfDateException) {
	            throw e; // 重新拋出自定義異常
	        }
	        throw new JourneyOutOfDateException("日期格式錯誤：" + e.getMessage());
	    }
	}
	
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
		
		// 新增：獲取並驗證旅遊計劃
        CountryPlan countryPlan = countryPlanRepository.findById(journeyDTO.getCountryPlanId())
            .orElseThrow(() -> new CountryPlanNotFoundException("找不到關聯的旅遊計劃"));
        
        // 新增：驗證日期和天數
        validateJourneyDate(journeyDTO, countryPlan);
		
		Journey journey = journeyMapper.toEntity(journeyDTO);
		journeyRepository.save(journey);
		journeyRepository.flush();
	}

	@Override
	public void updateJourney(Integer journeyId, JourneyDTO journeyDTO) {
		Journey existing = journeyRepository.findById(journeyId)
				.orElseThrow(() -> new JourneyNotFoundException("修改失敗：行程" + journeyId + "不存在"));
		
		// 新增：獲取並驗證旅遊計劃
        CountryPlan countryPlan;
        if (journeyDTO.getCountryPlanId() != null) {
            countryPlan = countryPlanRepository.findById(journeyDTO.getCountryPlanId())
                .orElseThrow(() -> new CountryPlanNotFoundException("找不到關聯的旅遊計劃"));
        } else {
            countryPlan = existing.getCountryPlan(); // 使用現有的計劃
        }
        
        // 新增：驗證日期和天數
        validateJourneyDate(journeyDTO, countryPlan);
		
		existing.setLocation(journeyDTO.getLocation());
		existing.setAttraction(journeyDTO.getAttraction());
		existing.setMemo(journeyDTO.getMemo());
		existing.setDays(journeyDTO.getDays());
		existing.setTime(journeyDTO.getTime());
		existing.setDays(journeyDTO.getDays());
		
	
		journeyRepository.save(existing);
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
