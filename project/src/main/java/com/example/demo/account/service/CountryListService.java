package com.example.demo.account.service;

import java.util.List;
import com.example.demo.account.model.dto.CountryListDTO;

public interface CountryListService {
    
    // 用戶相關方法
    List<CountryListDTO> findAllCountryByUserId(Integer userId);
    CountryListDTO getCountryByIdAndUserId(Integer countryId, Integer userId);
    CountryListDTO addCountryListForUser(CountryListDTO countryListDTO, Integer userId);
    CountryListDTO updateCountryListForUser(Integer countryId, CountryListDTO countryListDTO, Integer userId);
    void deleteCategoryForUser(Integer countryId, Integer userId);
    
    // 保留原有方法（管理員功能）
    List<CountryListDTO> findAllCountry();
    CountryListDTO getCountryById(Integer countryId);
    void addCountryList(CountryListDTO countryListDTO);
    void updateCountryList(Integer countryId, CountryListDTO countryListDTO);
    void deleteCategory(Integer countryId);
}