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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.account.except.money.MoneyException;
import com.example.demo.account.model.dto.TravelMoneyDTO;
import com.example.demo.account.response.ApiResponse;
import com.example.demo.account.service.TravelMoneyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = {"/travelMoney"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8085"}, allowCredentials = "true")

public class TravelMoneyController {

	@Autowired
	private TravelMoneyService travelMoneyService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<TravelMoneyDTO>>> findAllTravelMoney(){
		List<TravelMoneyDTO> travelMoneyDTOs = travelMoneyService.findAllTravelMoney();
		String message = travelMoneyDTOs.isEmpty()? "查無金額":"查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, travelMoneyDTOs));
	}
	
	@GetMapping("/{moneyId}")
	public ResponseEntity<ApiResponse<TravelMoneyDTO>> getMoney(@PathVariable Integer moneyId){
		TravelMoneyDTO travelMoneyDTO = travelMoneyService.getTravelMoneyById(moneyId);
		return ResponseEntity.ok(ApiResponse.success("查詢該筆金額成功", travelMoneyDTO));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<TravelMoneyDTO>> addMoney(@Valid @RequestBody TravelMoneyDTO travelMoneyDTO, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors()) {
			throw new MoneyException("新增失敗：" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		travelMoneyService.addTravelMoney(travelMoneyDTO);
		return ResponseEntity.ok(ApiResponse.success("新增成功", travelMoneyDTO));
	}
	
	@PutMapping("/{moneyId}")
	public ResponseEntity<ApiResponse<TravelMoneyDTO>> updateMoney(@PathVariable Integer moneyId, @Valid @RequestBody TravelMoneyDTO travelMoneyDTO, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors()) {
			throw new MoneyException("修改失敗：" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		travelMoneyService.updateTravelMoney(moneyId, travelMoneyDTO);
		return ResponseEntity.ok(ApiResponse.success("修改成功", travelMoneyDTO));
	}
	
	@DeleteMapping("/{moneyId}")
	public ResponseEntity<ApiResponse<Integer>> deleteMoney(@PathVariable Integer moneyId)
	{
		travelMoneyService.deleteTravelMoney(moneyId);
		return ResponseEntity.ok(ApiResponse.success("刪除成功", moneyId));
	}
	
	@ExceptionHandler({MoneyException.class})
	public ResponseEntity<ApiResponse<Void>> handleMoneyException(MoneyException e)
	{
		return ResponseEntity.ok(ApiResponse.error(500, e.getMessage()));
	}
}
