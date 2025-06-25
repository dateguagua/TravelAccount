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

import com.example.demo.account.except.category.CategoryAlreadyExistsException;
import com.example.demo.account.except.category.CategoryNotFoundException;
import com.example.demo.account.model.dto.CategoryDTO;
import com.example.demo.account.model.dto.UsersCert;
import com.example.demo.account.model.entity.Users;
import com.example.demo.account.response.ApiResponse;
import com.example.demo.account.service.CategoryService;
import com.example.demo.account.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = {"/category"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8085"}, allowCredentials = "true")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserService userService;
	
	// 從 Session 獲取當前登入用戶的 ID
	private Integer getCurrentUserId(HttpServletRequest request) {
		System.out.println("=== Category Session Debug 開始 ===");
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.out.println("Session 為 null，用戶未登入");
			throw new CategoryNotFoundException("用戶未登入，請先登入");
		}
		
		// 先嘗試直接獲取 userId
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			System.out.println("從 Session 直接獲取 userId: " + userId);
			return userId;
		}
		
		// 如果沒有 userId，嘗試從 userCert 獲取
		UsersCert userCert = (UsersCert) session.getAttribute("userCert");
		if (userCert == null) {
			System.out.println("Session 中沒有 userCert");
			throw new CategoryNotFoundException("用戶登入狀態已過期，請重新登入");
		}
		
		// 根據 userName 查詢用戶 ID
		String userName = userCert.getUsername();
		Users user = userService.findUserByUserName(userName);
		
		// 把 userId 存到 session 中
		session.setAttribute("userId", user.getUserId());
		
		System.out.println("從 userCert 獲取並設定 userId: " + user.getUserId());
		System.out.println("=== Category Session Debug 結束 ===");
		return user.getUserId();
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<CategoryDTO>>> findAllCategory(HttpServletRequest request){
		Integer userId = getCurrentUserId(request);
		List<CategoryDTO> categoryDTOs = categoryService.findAllCategoryByUserId(userId);
		String message = categoryDTOs.isEmpty()? "查無分類資料":"查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, categoryDTOs));
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<CategoryDTO>> getCategory(
			@PathVariable Integer categoryId, 
			HttpServletRequest request) {
		Integer userId = getCurrentUserId(request);
		CategoryDTO categoryDTO = categoryService.getCategoryByIdAndUserId(categoryId, userId);
		return ResponseEntity.ok(ApiResponse.success("查詢分類成功", categoryDTO));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<CategoryDTO>> addCategory(
			@Valid @RequestBody CategoryDTO categoryDTO, 
			BindingResult bindingResult,
			HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			throw new CategoryAlreadyExistsException("新增失敗：" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		Integer userId = getCurrentUserId(request);
		CategoryDTO result = categoryService.addCategoryForUser(categoryDTO, userId);
		return ResponseEntity.ok(ApiResponse.success("分類新增成功", result));
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(
			@PathVariable Integer categoryId,
			@Valid @RequestBody CategoryDTO categoryDTO, 
			BindingResult bindingResult,
			HttpServletRequest request) {
		
		System.out.println("=== 分類編輯調試開始 ===");
		System.out.println("接收到的 categoryId: " + categoryId);
		System.out.println("接收到的 categoryDTO: " + categoryDTO);
		
		if(bindingResult.hasErrors()) {
			throw new CategoryAlreadyExistsException("修改失敗："+bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		
		Integer userId = getCurrentUserId(request);
		System.out.println("當前登入用戶 ID: " + userId);
		System.out.println("========================");
		
		CategoryDTO result = categoryService.updateCategoryForUser(categoryId, categoryDTO, userId);
		return ResponseEntity.ok(ApiResponse.success("分類修改成功", result));
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<Integer>> deleteCategory(
			@PathVariable Integer categoryId,
			HttpServletRequest request) {
		Integer userId = getCurrentUserId(request);
		categoryService.deleteCategoryForUser(categoryId, userId);
		return ResponseEntity.ok(ApiResponse.success("分類刪除成功", categoryId));
	}
	
	@ExceptionHandler({CategoryNotFoundException.class, CategoryAlreadyExistsException.class})
	public ResponseEntity<ApiResponse<Void>> handleCategoryExceptions(RuntimeException e) {
		return ResponseEntity.ok(ApiResponse.error(500, e.getMessage()));
	}
}