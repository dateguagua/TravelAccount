package com.example.demo.account.except;

public class UserNotFoundException extends Exception{

	public UserNotFoundException(String message) {
		super(message);
	}
}
