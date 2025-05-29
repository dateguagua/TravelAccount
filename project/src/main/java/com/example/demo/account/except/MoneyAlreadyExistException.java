package com.example.demo.account.except;

public class MoneyAlreadyExistException extends Exception{

	public MoneyAlreadyExistException(String message) {
		super(message);
	}
}
