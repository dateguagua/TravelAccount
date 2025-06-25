package com.example.demo.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.account.model.entity.CountryList;

@Repository
public interface CountryListRepository extends JpaRepository<CountryList, Integer>{
	
	// 原有方法
	@Query(value = "select country from country_list where country_id = :countryId", nativeQuery = true)
	String getCountry(@Param("countryId") Integer countryId);
	
	boolean existsByCountryName(String countryName);
	
	// === 用戶相關查詢方法（一對多關係）===
	
	/**
	 * 查找某個用戶的所有國家
	 */
	List<CountryList> findByUser_UserId(Integer userId);
	
	/**
	 * 查找特定國家且屬於特定用戶
	 */
	Optional<CountryList> findByCountryIdAndUser_UserId(Integer countryId, Integer userId);
	
	/**
	 * 檢查某個用戶是否已經有特定名稱的國家
	 */
	boolean existsByCountryNameAndUser_UserId(String countryName, Integer userId);
	
	/**
	 * 檢查某個用戶是否已經有特定名稱的國家（排除特定ID的國家）
	 * 用於更新時檢查重複
	 */
	boolean existsByCountryNameAndUser_UserIdAndCountryIdNot(String countryName, Integer userId, Integer countryId);
}