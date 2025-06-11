package com.example.demo.account.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.account.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	//取得帳目類別
	@Query(value = "select categoryName from category where category_id =:categoryId", nativeQuery = true)
	Integer getCategory(Integer categoryId);
	
	boolean existsByCategoryName(String category);

}
