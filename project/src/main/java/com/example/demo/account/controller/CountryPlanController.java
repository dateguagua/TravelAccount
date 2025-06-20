package com.example.demo.account.controller;

import java.security.cert.Certificate;
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

import com.example.demo.account.except.countryPlan.CountryPlanException;
import com.example.demo.account.model.dto.CountryPlanDTO;
import com.example.demo.account.model.dto.UsersCert;
import com.example.demo.account.response.ApiResponse;
import com.example.demo.account.service.CountryPlanService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = {"/countryPlan"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8085"}, allowCredentials = "true")

public class CountryPlanController {

	@Autowired
	private CountryPlanService countryPlanService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<CountryPlanDTO>>> findByUserCountrys(HttpSession session)
	{
		UsersCert cert = (UsersCert) session.getAttribute("userCert");
		
	    if (cert == null) {
	        return ResponseEntity
	            .status(401)
	            .body(ApiResponse.error(401, "尚未登入"));
	    }

	    // 呼叫 Service 用 userId 找計畫
		List<CountryPlanDTO> countryPlanDTOs = countryPlanService.findByUserUserId(cert.getId());
		String message = countryPlanDTOs.isEmpty()? "查無計劃":"查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, countryPlanDTOs));
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<ApiResponse<List<CountryPlanDTO>>> findAllCountryPlan(){
		List<CountryPlanDTO> countryPlanDTOs = countryPlanService.findAllCountryPlan();
		String message = countryPlanDTOs.isEmpty()? "查無計劃":"查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, countryPlanDTOs));
	}
	
	@GetMapping("findAll/{countryPlanId}")
	public ResponseEntity<ApiResponse<CountryPlanDTO>> getCountryPlan(@PathVariable Integer countryPlanId)
	{
		CountryPlanDTO countryPlanDTO = countryPlanService.getCountryPlanById(countryPlanId);
		return ResponseEntity.ok(ApiResponse.success("查詢該計畫成功", countryPlanDTO));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<CountryPlanDTO>> addCountryPlan(@Valid @RequestBody CountryPlanDTO countryPlanDTO, BindingResult bindingResult, HttpSession session)
	{
	    if(bindingResult.hasErrors()) {
	        throw new CountryPlanException("新增失敗："+ bindingResult.getAllErrors().get(0).getDefaultMessage());
	    }
	    
	    // 從 session 獲取當前用戶資訊
	    UsersCert cert = (UsersCert) session.getAttribute("userCert");
	    if (cert == null) {
	        return ResponseEntity
	            .status(401)
	            .body(ApiResponse.error(401, "尚未登入"));
	    }
	    
	    // 設定用戶資訊到 DTO（注意欄位名稱）
	    countryPlanDTO.setUserId(cert.getId());
	    countryPlanDTO.setUserName(cert.getUsername()); // UsersCert 用的是 username
	    
	    countryPlanService.addCountryPlan(countryPlanDTO);
	    return ResponseEntity.ok(ApiResponse.success("新增成功", countryPlanDTO));
	}
	
	@PutMapping("/{countryPlanId}")
	public ResponseEntity<ApiResponse<CountryPlanDTO>> updateCountryPlan(@PathVariable Integer countryPlanId,@Valid @RequestBody CountryPlanDTO countryPlanDTO, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors()) {
			throw new CountryPlanException("修改失敗：" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		countryPlanService.updateCountryPlan(countryPlanId, countryPlanDTO);
		return ResponseEntity.ok(ApiResponse.success("修改成功", countryPlanDTO));
	}
	
	@DeleteMapping("/{countryPlanId}")
	public ResponseEntity<ApiResponse<Integer>> deleteCountryPlan(@PathVariable Integer countryPlanId)
	{
		countryPlanService.deleteCountryPlan(countryPlanId);
		return ResponseEntity.ok(ApiResponse.success("刪除成功", countryPlanId));
	}
	
	@ExceptionHandler({CountryPlanException.class})
	public ResponseEntity<ApiResponse<Void>> handleCountryPlanExceptions(CountryPlanException e)
	{
		return ResponseEntity.ok(ApiResponse.error(500, e.getMessage()));
	}
	
}
