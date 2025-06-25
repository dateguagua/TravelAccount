package com.example.demo.account.service;

import java.util.List;
import com.example.demo.account.model.dto.CategoryDTO;

public interface CategoryService {
    
    // 用戶相關方法
    List<CategoryDTO> findAllCategoryByUserId(Integer userId);
    CategoryDTO getCategoryByIdAndUserId(Integer categoryId, Integer userId);
    CategoryDTO addCategoryForUser(CategoryDTO categoryDTO, Integer userId);
    CategoryDTO updateCategoryForUser(Integer categoryId, CategoryDTO categoryDTO, Integer userId);
    void deleteCategoryForUser(Integer categoryId, Integer userId);
    
    // 保留原有方法（管理員功能）
    List<CategoryDTO> findAllCategory();
    CategoryDTO getCategoryById(Integer categoryId);
    void addCategory(CategoryDTO categoryDTO);
    void updateCategory(Integer categoryId, CategoryDTO categoryDTO);
    void deleteCategory(Integer categoryId);
}