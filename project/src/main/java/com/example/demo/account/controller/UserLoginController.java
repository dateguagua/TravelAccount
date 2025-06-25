package com.example.demo.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.account.except.users.CertException;
import com.example.demo.account.model.dto.UsersCert;
import com.example.demo.account.model.dto.UsersDTO;
import com.example.demo.account.model.entity.Users;
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
	
	@GetMapping("/user")
	public ResponseEntity<ApiResponse<List<UsersDTO>>> findAllUser(){
		List<UsersDTO> usersDTOs = userService.findAllUser();
		String message = usersDTOs.isEmpty()? "查無此Player":"查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, usersDTOs));
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Void>> login(
			@RequestParam String userName, 
			@RequestParam String password, 
			HttpSession session) {
		try {
			// 驗證用戶憑證
			UsersCert cert = certService.getCert(userName, password);
			
			// 根據 userName 查詢完整用戶資訊
			Users user = userService.findUserByUserName(userName);
			
			// 存入 Session
			session.setAttribute("userCert", cert);
			session.setAttribute("userId", user.getUserId()); // 同時存入 userId
			
			// Debug 資訊
			System.out.println("=== 登入成功 ===");
			System.out.println("Session ID: " + session.getId());
			System.out.println("User ID: " + user.getUserId());
			System.out.println("User Name: " + user.getUserName());
			System.out.println("Session 設定完成");
			System.out.println("=================");
			
			return ResponseEntity.ok(ApiResponse.success("登入成功", null));
		} catch (CertException e) {
			System.out.println("登入失敗：" + e.getMessage());
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(401, "登入失敗:" + e.getMessage()));
		} catch (Exception e) {
			System.out.println("登入過程發生錯誤：" + e.getMessage());
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ApiResponse.error(500, "登入過程發生錯誤：" + e.getMessage()));
		}
	}
	
	@GetMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
		try {
			if (session.getAttribute("userCert") == null) {
				return ResponseEntity
						.status(HttpStatus.UNAUTHORIZED)
						.body(ApiResponse.error(401, "登出失敗：用戶未登入"));
			}
			
			System.out.println("=== 登出 ===");
			System.out.println("Session ID: " + session.getId());
			System.out.println("清除 Session");
			
			session.invalidate();
			
			System.out.println("登出成功");
			System.out.println("=============");
			
			return ResponseEntity.ok(ApiResponse.success("登出成功", null));
		} catch (Exception e) {
			System.out.println("登出過程發生錯誤：" + e.getMessage());
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ApiResponse.error(500, "登出過程發生錯誤：" + e.getMessage()));
		}
	}
	
	@GetMapping("/check-login")
	public ResponseEntity<ApiResponse<Boolean>> checkLogin(HttpSession session) {
		try {
			boolean loggedIn = session.getAttribute("userCert") != null;
			Integer userId = (Integer) session.getAttribute("userId");
			
			System.out.println("=== 檢查登入狀態 ===");
			System.out.println("Session ID: " + session.getId());
			System.out.println("已登入: " + loggedIn);
			System.out.println("User ID: " + userId);
			System.out.println("===================");
			
			return ResponseEntity.ok(ApiResponse.success("檢查登入", loggedIn));
		} catch (Exception e) {
			System.out.println("檢查登入狀態發生錯誤：" + e.getMessage());
			return ResponseEntity.ok(ApiResponse.success("檢查登入", false));
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<Void>> register(
			@RequestParam String userName, 
			@RequestParam String password, 
			@RequestParam String confirmPassword) {
		try {
			// 檢查兩次密碼是否相同
			if (!password.equals(confirmPassword)) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(ApiResponse.error(400, "兩次輸入的密碼不相同"));
			}
			
			System.out.println("=== 註冊新用戶 ===");
			System.out.println("用戶名: " + userName);
			
			userService.addUser(userName, password);
			
			System.out.println("註冊成功");
			System.out.println("==================");
			
			return ResponseEntity.ok(ApiResponse.success("註冊成功", null));
		} catch (Exception e) {
			System.out.println("註冊失敗：" + e.getMessage());
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(ApiResponse.error(400, "註冊失敗：" + e.getMessage()));
		}
	}
	
	// 新增：獲取當前登入用戶資訊
	@GetMapping("/current-user")
	public ResponseEntity<ApiResponse<UsersDTO>> getCurrentUser(HttpSession session) {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				return ResponseEntity
						.status(HttpStatus.UNAUTHORIZED)
						.body(ApiResponse.error(401, "用戶未登入"));
			}
			
			Users user = userService.findUserById(userId);
			// 這裡需要將 Users 轉換為 UsersDTO，你可能需要一個 mapper
			// UsersDTO userDTO = userMapper.toDto(user);
			
			return ResponseEntity.ok(ApiResponse.success("獲取用戶資訊成功", null)); // 暫時返回 null
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ApiResponse.error(500, "獲取用戶資訊失敗：" + e.getMessage()));
		}
	}
	
	@ExceptionHandler({CertException.class})
	public ResponseEntity<ApiResponse<Void>> handleCertExceptions(CertException e) {
		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(ApiResponse.error(401, e.getMessage()));
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<ApiResponse<Void>> handleGeneralExceptions(Exception e) {
		System.out.println("Controller 發生未處理的錯誤：" + e.getMessage());
		e.printStackTrace();
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.error(500, "系統錯誤：" + e.getMessage()));
	}
}