package com.example.demo.account.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.account.model.entity.Category;
import com.example.demo.account.model.entity.CountryPlan;
import com.example.demo.account.model.entity.Journey;

@Repository
public interface TravelMoneyRepository extends JpaRepository<Journey, Integer>{
	
	//取得行程計劃
	@Query(value = "select journey_id, attraction, country_plan_id, location, memo, days, time, is_delete from journey where journey_id =:journeyId", nativeQuery = true)
	List<Object[]> getJourney(@Param("journeyId") Integer journeyId);

}
