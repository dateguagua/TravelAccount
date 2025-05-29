package com.example.demo.account.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.account.model.dto.CategoryDTO;
import com.example.demo.account.model.entity.Category;

@Component
public class CategoryMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public CategoryDTO toDto(Category category) {
		return modelMapper.map(category, CategoryDTO.class);
	}
	
	public Category toEntity(CategoryDTO categoryDTO) {
		return modelMapper.map(categoryDTO, Category.class);
	}
}
