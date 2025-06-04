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

import com.example.demo.account.except.journey.JourneyException;
import com.example.demo.account.model.dto.JourneyDTO;
import com.example.demo.account.response.ApiResponse;
import com.example.demo.account.service.JourneyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = {"/journey"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8085"}, allowCredentials = "true")
public class JourneyController {

	@Autowired
	private JourneyService journeyService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<JourneyDTO>>> findAllJourney(){
		List<JourneyDTO> journeyDTOs = journeyService.findAllJourney();
		String message = journeyDTOs.isEmpty()?"查無行程":"查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, journeyDTOs));
	}
	
	@GetMapping("/{journeyId}")
	public ResponseEntity<ApiResponse<JourneyDTO>> getJourney(@PathVariable Integer journeyId){
		JourneyDTO journeyDTO = journeyService.getJourneyById(journeyId) ;
		return ResponseEntity.ok(ApiResponse.success("查詢該行程成功", journeyDTO));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<JourneyDTO>> addJourney(@Valid @RequestBody JourneyDTO journeyDTO, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors()) {
			throw new JourneyException("新增失敗：" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		journeyService.addJourney(journeyDTO);
		return ResponseEntity.ok(ApiResponse.success("新增行程成功", journeyDTO));
	}
	
	@PutMapping("/{journeyId}")
	public ResponseEntity<ApiResponse<JourneyDTO>> updateJourney(@PathVariable Integer journeyId, @Valid @RequestBody JourneyDTO journeyDTO,BindingResult bindingResult)
	{
		if(bindingResult.hasErrors()) {
			throw new JourneyException("修改失敗：" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		journeyService.updateJourney(journeyId, journeyDTO);
		return ResponseEntity.ok(ApiResponse.success("修改行程成功", journeyDTO));
	}
	
	@DeleteMapping("/{journeyId}")
	public ResponseEntity<ApiResponse<Integer>> deleteJourney(@PathVariable Integer journeyId){
		journeyService.deleteJourney(journeyId);
		return ResponseEntity.ok(ApiResponse.success("刪除行程成功", journeyId));
	}
	
	@ExceptionHandler({JourneyException.class})
	public ResponseEntity<ApiResponse<Void>> handleJourneyException(JourneyException e)
	{
		return ResponseEntity.ok(ApiResponse.error(500, e.getMessage()));
	}
	
	
}
