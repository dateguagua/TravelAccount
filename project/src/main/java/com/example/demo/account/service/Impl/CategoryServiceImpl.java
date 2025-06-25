package com.example.demo.account.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.except.category.CategoryAlreadyExistsException;
import com.example.demo.account.except.category.CategoryNotFoundException;
import com.example.demo.account.mapper.CategoryMapper;
import com.example.demo.account.model.dto.CategoryDTO;
import com.example.demo.account.model.entity.Category;
import com.example.demo.account.model.entity.Users;
import com.example.demo.account.repository.CategoryRepository;
import com.example.demo.account.repository.UserRepository;
import com.example.demo.account.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;  // 新增用戶 Repository
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	// === 用戶相關方法實作 ===
	
	@Override
	public List<CategoryDTO> findAllCategoryByUserId(Integer userId) {
		List<Category> categories = categoryRepository.findByUser_UserId(userId);
		return categories.stream()
						.map(categoryMapper::toDto)
						.toList();
	}
	
	@Override
	public CategoryDTO getCategoryByIdAndUserId(Integer categoryId, Integer userId) {
		Category category = categoryRepository.findByCategoryIdAndUser_UserId(categoryId, userId)
				.orElseThrow(() -> new CategoryNotFoundException("找不到該帳目類別或您沒有權限訪問：categoryId = " + categoryId));
		return categoryMapper.toDto(category);
	}
	
	@Override
	public CategoryDTO addCategoryForUser(CategoryDTO categoryDTO, Integer userId) {
		// 檢查用戶是否存在
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new CategoryNotFoundException("用戶不存在：UserId = " + userId));
		
		// 檢查該用戶是否已經有這個分類
		if(categoryRepository.existsByCategoryNameAndUser_UserId(categoryDTO.getCategoryName(), userId)) {
			throw new CategoryAlreadyExistsException("新增失敗：帳目類別 " + categoryDTO.getCategoryName() + " 已存在");
		}
		
		// 創建新的 Category 並設定用戶
		Category category = categoryMapper.toEntity(categoryDTO);
		category.setUser(user);  // 設定關聯的用戶
		
		Category saved = categoryRepository.save(category);
		categoryRepository.flush();
		
		return categoryMapper.toDto(saved);
	}
	
	@Override
	public CategoryDTO updateCategoryForUser(Integer categoryId, CategoryDTO categoryDTO, Integer userId) {
		// 檢查分類是否存在且屬於該用戶
		Category category = categoryRepository.findByCategoryIdAndUser_UserId(categoryId, userId)
				.orElseThrow(() -> new CategoryNotFoundException("更新失敗：帳目類別 " + categoryId + " 不存在或您沒有權限修改"));
		
		// 檢查新的分類名稱是否已被該用戶使用（排除當前要修改的分類）
		if(categoryRepository.existsByCategoryNameAndUser_UserIdAndCategoryIdNot(
				categoryDTO.getCategoryName(), userId, categoryId)) {
			throw new CategoryAlreadyExistsException("修改失敗：帳目類別 " + categoryDTO.getCategoryName() + " 已存在");
		}
		
		// 更新分類名稱
		category.setCategoryName(categoryDTO.getCategoryName());
		Category updated = categoryRepository.saveAndFlush(category);
		
		return categoryMapper.toDto(updated);
	}
	
	@Override
	public void deleteCategoryForUser(Integer categoryId, Integer userId) {
		Category category = categoryRepository.findByCategoryIdAndUser_UserId(categoryId, userId)
				.orElseThrow(() -> new CategoryNotFoundException("刪除失敗：帳目類別 " + categoryId + " 不存在或您沒有權限刪除"));
		
		categoryRepository.delete(category);
	}
	
	// === 保留原有方法（管理員功能）===
	
	@Override
	public List<CategoryDTO> findAllCategory() {
		return categoryRepository.findAll()
								.stream()
								.map(categoryMapper::toDto)
								.toList();
	}
	
	@Override
	public CategoryDTO getCategoryById(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)
								.orElseThrow(() -> new CategoryNotFoundException("找不到該帳目類別: categoryId=" + categoryId));
		
		return categoryMapper.toDto(category);
	}
	
	@Override
	public void addCategory(CategoryDTO categoryDTO) {
		if(categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
			throw new CategoryAlreadyExistsException("新增失敗：帳目類別"+ categoryDTO.getCategoryName()+"已存在");
		}
		Category category = categoryMapper.toEntity(categoryDTO);
		categoryRepository.save(category);
		categoryRepository.flush();
	}
	
	@Override
	public void updateCategory(Integer categoryId, CategoryDTO categoryDTO) {
		Optional<Category> optCategory = categoryRepository.findById(categoryId);
		if(optCategory.isEmpty()) {
			throw new CategoryNotFoundException("更新失敗：帳目類別"+ categoryId+"不存在");
		}
		if(categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
			throw new CategoryAlreadyExistsException("修改失敗：帳目類別"+ categoryDTO.getCategoryName()+"已存在");
		}
		categoryDTO.setCategoryId(categoryId);
		Category category = categoryMapper.toEntity(categoryDTO);
		categoryRepository.saveAndFlush(category);
	}
	
	@Override
	public void deleteCategory(Integer categoryId) {
		Optional<Category> optCategory = categoryRepository.findById(categoryId);
		if(optCategory.isEmpty()) {
			throw new CategoryNotFoundException("刪除失敗：帳目類別"+ categoryId+"不存在");
		}
		categoryRepository.deleteById(categoryId);
	}
}