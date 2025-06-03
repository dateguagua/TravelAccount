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

import com.example.demo.account.except.countryPlan.CountryPlanException;
import com.example.demo.account.model.dto.CountryPlanDTO;
import com.example.demo.account.response.ApiResponse;
import com.example.demo.account.service.CountryPlanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = {"/countryPlan"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8085"}, allowCredentials = "true")

public class CountryPlanController {

	@Autowired
	private CountryPlanService countryPlanService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<CountryPlanDTO>>> findAllCountryPlan(){
		List<CountryPlanDTO> countryPlanDTOs = countryPlanService.findAllCountryPlan();
		String message = countryPlanDTOs.isEmpty()? "查無行程":"查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, countryPlanDTOs));
	}
	
	@GetMapping("/{countryPlanId}")
	public ResponseEntity<ApiResponse<CountryPlanDTO>> getCountryPlan(@PathVariable Integer countryPlanId)
	{
		CountryPlanDTO countryPlanDTO = countryPlanService.getCountryPlanById(countryPlanId);
		return ResponseEntity.ok(ApiResponse.success("查詢該行程成功", countryPlanDTO));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<CountryPlanDTO>> addCountryPlan(@Valid @RequestBody CountryPlanDTO countryPlanDTO, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors()) {
			throw new CountryPlanException("新增失敗："+ bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
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
