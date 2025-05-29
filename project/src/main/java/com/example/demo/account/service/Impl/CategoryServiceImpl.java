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
import com.example.demo.account.repository.CategoryRepository;
import com.example.demo.account.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryMapper categoryMapper;
	
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
		Optional<Category> optCategory = categoryRepository.findById(categoryDTO.getCategoryId());
		if(optCategory.isPresent()) { //存在
			throw new CategoryAlreadyExistsException("新增失敗：帳目類別"+ categoryDTO.getCategoryId()+"已存在");
		}
		Category category = categoryMapper.toEntity(categoryDTO);
		categoryRepository.save(category);
		categoryRepository.flush();
	}

	@Override
	public void updateCategory(Integer categoryId, CategoryDTO categoryDTO) {
		Optional<Category> optCategory = categoryRepository.findById(categoryId);
		if(optCategory.isEmpty()) { //不存在
			throw new CategoryNotFoundException("更新失敗：帳目類別"+ categoryId+"不存在");
		}
		categoryDTO.setCategoryId(categoryId);
		Category category = categoryMapper.toEntity(categoryDTO);
		categoryRepository.saveAndFlush(category);
		
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Optional<Category> optCategory = categoryRepository.findById(categoryId);
		if(optCategory.isEmpty()) { //不存在
			throw new CategoryNotFoundException("刪除失敗：帳目類別"+ categoryId+"不存在");
		}
	
		categoryRepository.deleteById(categoryId);
		
	}

}
