package com.example.demo.account.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//建立server client在傳資料上的統一標準結構
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	private int status;
	private String message; //狀態
	private T data;  //payload實際資料
	
	public static <T> ApiResponse<T> success(String message, T data){
		return new ApiResponse<T>(200, message, data);
	}
	
	public static <T> ApiResponse<T> error(Integer status, String message){
		return new ApiResponse<T>(status, message, null);
	}
}
