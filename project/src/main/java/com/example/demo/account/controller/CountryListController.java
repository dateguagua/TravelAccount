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

import com.example.demo.account.except.countryList.CountryListException;
import com.example.demo.account.model.dto.CountryListDTO;
import com.example.demo.account.model.dto.UsersCert;
import com.example.demo.account.model.entity.Users;
import com.example.demo.account.response.ApiResponse;
import com.example.demo.account.service.CountryListService;
import com.example.demo.account.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = {"/countryList"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8085"}, allowCredentials = "true")
public class CountryListController {

	@Autowired
	private CountryListService countryListService;
	
	@Autowired
	private UserService userService;
	
	// 從 Session 獲取當前登入用戶的 ID
	private Integer getCurrentUserId(HttpServletRequest request) {
		System.out.println("=== Session Debug 開始 ===");
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.out.println("Session 為 null，用戶未登入");
			throw new CountryListException("用戶未登入，請先登入");
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
			throw new CountryListException("用戶登入狀態已過期，請重新登入");
		}
		
		// 根據 userName 查詢用戶 ID
		String userName = userCert.getUsername(); // 假設 UsersCert 有 getUserName() 方法
		Users user = userService.findUserByUserName(userName);
		
		// 把 userId 存到 session 中，下次就不用查詢了
		session.setAttribute("userId", user.getUserId());
		
		System.out.println("從 userCert 獲取並設定 userId: " + user.getUserId());
		System.out.println("=== Session Debug 結束 ===");
		return user.getUserId();
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<CountryListDTO>>> findAllCountryList(HttpServletRequest request){
		Integer userId = getCurrentUserId(request);
		List<CountryListDTO> countryListDTOs = countryListService.findAllCountryByUserId(userId);
		String message = countryListDTOs.isEmpty()? "查無國家資料":"查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, countryListDTOs));
	}
	
	@GetMapping("/{countryId}")
	public ResponseEntity<ApiResponse<CountryListDTO>> getCountryList(
			@PathVariable Integer countryId, 
			HttpServletRequest request) {
		Integer userId = getCurrentUserId(request);
		CountryListDTO countryListDTO = countryListService.getCountryByIdAndUserId(countryId, userId);
		return ResponseEntity.ok(ApiResponse.success("查詢國家成功", countryListDTO));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<CountryListDTO>> addCountryList(
			@Valid @RequestBody CountryListDTO countryListDTO, 
			BindingResult bindingResult,
			HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			throw new CountryListException("新增失敗：" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		Integer userId = getCurrentUserId(request);
		CountryListDTO result = countryListService.addCountryListForUser(countryListDTO, userId);
		return ResponseEntity.ok(ApiResponse.success("國家新增成功", result));
	}
	
	@PutMapping("/{countryId}")
	public ResponseEntity<ApiResponse<CountryListDTO>> updateCountry(
			@PathVariable Integer countryId,
			@Valid @RequestBody CountryListDTO countryListDTO, 
			BindingResult bindingResult,
			HttpServletRequest request) {
		System.out.println("=== 編輯調試開始 ===");
		System.out.println("接收到的 countryId: " + countryId);
		System.out.println("接收到的 countryListDTO: " + countryListDTO);
		
		if(bindingResult.hasErrors()) {
			throw new CountryListException("修改失敗："+bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		
		Integer userId = getCurrentUserId(request);
		System.out.println("當前登入用戶 ID: " + userId);
		System.out.println("========================");
		
		CountryListDTO result = countryListService.updateCountryListForUser(countryId, countryListDTO, userId);
		return ResponseEntity.ok(ApiResponse.success("國家修改成功", result));
	}
	
	@DeleteMapping("/{countryId}")
	public ResponseEntity<ApiResponse<Integer>> deleteCountry(
			@PathVariable Integer countryId,
			HttpServletRequest request) {
		Integer userId = getCurrentUserId(request);
		countryListService.deleteCategoryForUser(countryId, userId);
		return ResponseEntity.ok(ApiResponse.success("國家刪除成功", countryId));
	}
	
	@ExceptionHandler({CountryListException.class})
	public ResponseEntity<ApiResponse<Void>> handleCountryListExceptions(CountryListException e) {
		return ResponseEntity.ok(ApiResponse.error(500, e.getMessage()));
	}
}