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
import com.example.demo.account.response.ApiResponse;
import com.example.demo.account.service.CountryListService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = {"/countryList"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8085"}, allowCredentials = "true")
public class CountryListController {

	@Autowired
	private CountryListService countryListService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<CountryListDTO>>> findAllCountryList(){
		List<CountryListDTO> countryListDTOs = countryListService.findAllCountry();
		String message = countryListDTOs.isEmpty()? "查無國家資料":"查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, countryListDTOs));
	}
	
	@GetMapping("/{countryId}")
	public ResponseEntity<ApiResponse<CountryListDTO>> getCountryList(@PathVariable Integer countryId)
	{
		CountryListDTO countryListDTO = countryListService.getCountryById(countryId);
		return ResponseEntity.ok(ApiResponse.success("查詢國家成功", countryListDTO));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<CountryListDTO>> addCountryList(@Valid @RequestBody CountryListDTO countryListDTO, BindingResult bindingResult )
	{
		if(bindingResult.hasErrors()) {
			throw new CountryListException("新增失敗：" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		countryListService.addCountryList(countryListDTO);
		return ResponseEntity.ok(ApiResponse.success("國家新增成功", countryListDTO));
	}
	
	@PutMapping("/{countryId}")
	public ResponseEntity<ApiResponse<CountryListDTO>> updateCountry(@PathVariable Integer countryId,@Valid @RequestBody CountryListDTO countryListDTO, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors()) {
			throw new CountryListException("修改失敗："+bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		countryListService.updateCountryList(countryId, countryListDTO);
		return ResponseEntity.ok(ApiResponse.success("國家修改成功", countryListDTO));
	}
	
	@DeleteMapping("/{countryId}")
	public ResponseEntity<ApiResponse<Integer>> deleteCountry(@PathVariable Integer countryId)
	{
		countryListService.deleteCategory(countryId);
		return ResponseEntity.ok(ApiResponse.success("國家刪除成功", countryId));
	}
	
	@ExceptionHandler({CountryListException.class})
	public ResponseEntity<ApiResponse<Void>> handleCountryListExceptions(CountryListException e)
	{
		return ResponseEntity.ok(ApiResponse.error(500, e.getMessage()));
	}
}
