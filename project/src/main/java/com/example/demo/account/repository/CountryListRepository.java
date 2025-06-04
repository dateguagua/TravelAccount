package com.example.demo.account.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.account.model.entity.Category;
import com.example.demo.account.model.entity.CountryList;
import com.example.demo.account.model.entity.Users;

@Repository
public interface CountryListRepository extends JpaRepository<CountryList, Integer>{
	
	//取得國家
	@Query(value = "select country from country where country_id  =: countryId", nativeQuery = true)
	Integer getCountry(Integer countryId);
	
	boolean existsByCountry(String country);
}
