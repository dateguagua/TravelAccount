package com.example.demo.account.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.account.model.entity.Category;
import com.example.demo.account.model.entity.CountryPlan;
import com.example.demo.account.model.entity.Journey;
import com.example.demo.account.model.entity.TravelMoney;

@Repository
public interface TravelMoneyRepository extends JpaRepository<TravelMoney, Integer>{
	
	//取得行程計劃
	@Query(value = "select money_id, user_id, category_id, dollar, money_date, product_name from travel_money where money_id =:moneyId", nativeQuery = true)
	List<Object[]> getTravelMoney(@Param("moneyId") Integer moneyId);

}
