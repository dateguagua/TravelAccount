package com.example.demo.account.service;

import java.util.List;

import com.example.demo.account.model.dto.CategoryDTO;

public interface CategoryService {

	public List<CategoryDTO> findAllCategory();
	public CategoryDTO getCategoryById(Integer categoryId);
	
	public void addCategory(CategoryDTO categoryDTO);
	public void updateCategory(Integer categoryId, CategoryDTO categoryDTO);
	public void deleteCategory(Integer categoryId);
} 
