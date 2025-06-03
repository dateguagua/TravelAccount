package com.example.demo.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.account.except.category.CategoryException;
import com.example.demo.account.model.dto.CategoryDTO;
import com.example.demo.account.response.ApiResponse;
import com.example.demo.account.service.CategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = {"/category"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8085"}, allowCredentials = "true")

public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	//取得ＡＬＬ
	@GetMapping
	public ResponseEntity<ApiResponse<List<CategoryDTO>>> findAllCategory(){
		List<CategoryDTO> categoryDTOs = categoryService.findAllCategory();
		String message = categoryDTOs.isEmpty()?"查無此帳目類別":"查詢成功，有此帳目類別";
		return ResponseEntity.ok(ApiResponse.success(message, categoryDTOs));
	}
	
	//取得單筆
	@GetMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<CategoryDTO>> getCategory(@PathVariable Integer categoryId){
		CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok(ApiResponse.success("帳目類別單筆查詢成功", categoryDTO));
	}
	
	//新增
	@PostMapping
	public ResponseEntity<ApiResponse<CategoryDTO>> addCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors()) {
			throw new CategoryException("新增失敗：" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		categoryService.addCategory(categoryDTO);
		return ResponseEntity.ok(ApiResponse.success("帳目類別新增成功", categoryDTO));
	}
	
	//修改
	@PutMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(@PathVariable Integer categoryId, @Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors()) {
			throw new CategoryException("修改失敗" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		categoryService.updateCategory(categoryId, categoryDTO);
		return ResponseEntity.ok(ApiResponse.success("帳目類別修改成功", categoryDTO));
	}
	
	//刪除
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<Integer>>deleteCategory(@PathVariable Integer categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok(ApiResponse.success("帳目類別刪除成功", categoryId));
	}
	
	//錯誤處理
	@ExceptionHandler({CategoryException.class})
	public ResponseEntity<ApiResponse<Void>> handleCategoryExceptions(CategoryException e)
	{
		return ResponseEntity.ok(ApiResponse.error(500, e.getMessage()));
	}
}
