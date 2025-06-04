package com.example.demo.account.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.account.model.entity.Category;
import com.example.demo.account.model.entity.CountryPlan;

@Repository
public interface CountryPlanRepository extends JpaRepository<CountryPlan, Integer>{
	
	//取得旅行計劃
	@Query(value = "select country_plan_id, user_id, country_id, start_time, total_days from country where country_id =:countryId", nativeQuery = true)
	List<Object[]> getCountryPlan(@Param("countryPlanId") Integer countryPlanId);

	boolean existsByCountryPlanId(Integer countryPlanId);
}
