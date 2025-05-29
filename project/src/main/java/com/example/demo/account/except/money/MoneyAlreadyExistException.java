package com.example.demo.account.except.money;

public class MoneyAlreadyExistException extends MoneyException{

	public MoneyAlreadyExistException(String message) {
		super(message);
	}
}
