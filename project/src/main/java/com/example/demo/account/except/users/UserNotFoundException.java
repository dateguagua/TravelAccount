package com.example.demo.account.except.users;

public class UserNotFoundException extends CertException{

	public UserNotFoundException(String message) {
		super(message);
	}
}
