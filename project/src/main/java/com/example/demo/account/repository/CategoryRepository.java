package com.example.demo.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.account.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	// 原有方法
	boolean existsByCategoryName(String categoryName);
	
	// === 用戶相關查詢方法（一對多關係）===
	
	/**
	 * 查找某個用戶的所有分類
	 */
	List<Category> findByUser_UserId(Integer userId);
	
	/**
	 * 查找特定分類且屬於特定用戶
	 */
	Optional<Category> findByCategoryIdAndUser_UserId(Integer categoryId, Integer userId);
	
	/**
	 * 檢查某個用戶是否已經有特定名稱的分類
	 */
	boolean existsByCategoryNameAndUser_UserId(String categoryName, Integer userId);
	
	/**
	 * 檢查某個用戶是否已經有特定名稱的分類（排除特定ID的分類）
	 * 用於更新時檢查重複
	 */
	boolean existsByCategoryNameAndUser_UserIdAndCategoryIdNot(String categoryName, Integer userId, Integer categoryId);
}