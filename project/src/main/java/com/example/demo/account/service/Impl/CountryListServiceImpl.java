package com.example.demo.account.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.except.countryList.CountryListAlreadyExistException;
import com.example.demo.account.except.countryList.CountryListNotFoundException;
import com.example.demo.account.mapper.CountryListMapper;
import com.example.demo.account.model.dto.CountryListDTO;
import com.example.demo.account.model.entity.CountryList;
import com.example.demo.account.model.entity.Users;
import com.example.demo.account.repository.CountryListRepository;
import com.example.demo.account.repository.UserRepository;
import com.example.demo.account.service.CountryListService;

@Service
public class CountryListServiceImpl implements CountryListService {
    
    @Autowired
    private CountryListRepository countryListRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CountryListMapper countryListMapper;
    
    // === 用戶相關方法 ===
    
    @Override
    public List<CountryListDTO> findAllCountryByUserId(Integer userId) {
        List<CountryList> countries = countryListRepository.findByUser_UserId(userId);
        return countries.stream()
                       .map(countryListMapper::toDto)
                       .toList();
    }
    
    @Override
    public CountryListDTO getCountryByIdAndUserId(Integer countryId, Integer userId) {
        CountryList countryList = countryListRepository.findByCountryIdAndUser_UserId(countryId, userId)
                .orElseThrow(() -> new CountryListNotFoundException("找不到這個國家或您沒有權限訪問：CountryListId = " + countryId));
        return countryListMapper.toDto(countryList);
    }
    
    @Override
    public CountryListDTO addCountryListForUser(CountryListDTO countryListDTO, Integer userId) {
        // 檢查用戶是否存在
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new CountryListNotFoundException("用戶不存在：UserId = " + userId));
        
        // 檢查該用戶是否已經有這個國家
        if(countryListRepository.existsByCountryNameAndUser_UserId(countryListDTO.getCountryName(), userId)) {
            throw new CountryListAlreadyExistException("新增國家名稱失敗：" + countryListDTO.getCountryName() + " 此國家已存在");
        }
        
        // 創建新的 CountryList 並設定用戶
        CountryList countryList = countryListMapper.toEntity(countryListDTO);
        countryList.setUser(user);  // 設定關聯的用戶
        
        CountryList saved = countryListRepository.save(countryList);
        countryListRepository.flush();
        
        return countryListMapper.toDto(saved);
    }
    
    @Override
    public CountryListDTO updateCountryListForUser(Integer countryId, CountryListDTO countryListDTO, Integer userId) {
        // 檢查國家是否存在且屬於該用戶
        CountryList countryList = countryListRepository.findByCountryIdAndUser_UserId(countryId, userId)
                .orElseThrow(() -> new CountryListNotFoundException("修改國家名稱失敗：" + countryId + " 此國家不存在或您沒有權限修改"));
        
        // 檢查新的國家名稱是否已被該用戶使用（排除當前要修改的國家）
        if(countryListRepository.existsByCountryNameAndUser_UserIdAndCountryIdNot(
                countryListDTO.getCountryName(), userId, countryId)) {
            throw new CountryListAlreadyExistException("修改國家名稱失敗：" + countryListDTO.getCountryName() + " 此國家已存在");
        }
        
        // 更新國家名稱
        countryList.setCountryName(countryListDTO.getCountryName());
        CountryList updated = countryListRepository.saveAndFlush(countryList);
        
        return countryListMapper.toDto(updated);
    }
    
    @Override
    public void deleteCategoryForUser(Integer countryId, Integer userId) {
        CountryList countryList = countryListRepository.findByCountryIdAndUser_UserId(countryId, userId)
                .orElseThrow(() -> new CountryListNotFoundException("刪除國家名稱失敗：" + countryId + " 此國家不存在或您沒有權限刪除"));
        
        countryListRepository.delete(countryList);
    }
    
    // === 保留原有方法 ===
    
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
                .orElseThrow(() -> new CountryListNotFoundException("找不到這個國家：CountryListId = " + countryId));
        return countryListMapper.toDto(countryList);
    }
    
    @Override
    public void addCountryList(CountryListDTO countryListDTO) {
        if(countryListRepository.existsByCountryName(countryListDTO.getCountryName())) {
            throw new CountryListAlreadyExistException("新增國家名稱失敗" + countryListDTO.getCountryName() + "此國家已存在");
        }
        
        CountryList countryList = countryListMapper.toEntity(countryListDTO);
        countryListRepository.save(countryList);
        countryListRepository.flush();
    }
    
    @Override
    public void updateCountryList(Integer countryId, CountryListDTO countryListDTO) {
        Optional<CountryList> optCountryList = countryListRepository.findById(countryId);
        if(optCountryList.isEmpty()) {
            throw new CountryListNotFoundException("修改國家名稱失敗:" + countryId + "此國家不存在");
        }
        if(countryListRepository.existsByCountryName(countryListDTO.getCountryName())) {
            throw new CountryListAlreadyExistException("修改國家名稱失敗"+countryListDTO.getCountryName()+"此國家已存在");
        }
        
        countryListDTO.setCountryId(countryId);
        CountryList countryList = countryListMapper.toEntity(countryListDTO);
        countryListRepository.saveAndFlush(countryList);
    }
    
    @Override
    public void deleteCategory(Integer countryId) {
        Optional<CountryList> optCountryList = countryListRepository.findById(countryId);
        if(optCountryList.isEmpty()) {
            throw new CountryListNotFoundException("刪除國家名稱失敗:" + countryId + "此國家不存在");
        }
        countryListRepository.deleteById(countryId);
    }
}