package com.example.demo.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.account.except.users.CertException;
import com.example.demo.account.model.dto.UsersCert;
import com.example.demo.account.response.ApiResponse;
import com.example.demo.account.service.CertService;
import com.example.demo.account.service.UserService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/account")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8085"}, allowCredentials = "true")
public class UserLoginController {

	@Autowired
	private CertService certService;

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Void>> login(@RequestParam String userName, @RequestParam String password, HttpSession session)
	{
		try {
			UsersCert cert = certService.getCert(userName, password);
			session.setAttribute("userCert", cert);
			return ResponseEntity.ok(ApiResponse.success("登入成功", null));
		} catch (CertException e) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(401, "登入失敗:" +e.getMessage()));
		}
	}
	
	@GetMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpSession session)
	{
		if (session.getAttribute("userCert")== null) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(401, "登出失敗"));
		}
		session.invalidate();
		return ResponseEntity.ok(ApiResponse.success("登出成功", null));
	}
	
	@GetMapping("/check-login")
	public ResponseEntity<ApiResponse<Boolean>> checkLogin(HttpSession session)
	{
		boolean loggedIn = session.getAttribute("userCert") != null;
		return ResponseEntity.ok(ApiResponse.success("檢查登入", loggedIn));
	}
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<Void>> register(@RequestParam String userName, @RequestParam String password, @RequestParam String email)
	{
		try {
			userService.addUser(userName, password, email);
			return ResponseEntity.ok(ApiResponse.success("註冊成功", null));
		} catch (Exception e) {
			return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ApiResponse.error(400, "註冊失敗：" + e.getMessage()));
	    }
	}
}
