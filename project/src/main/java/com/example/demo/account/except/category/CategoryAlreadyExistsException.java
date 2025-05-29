package com.example.demo.account.except.category;

public class CategoryAlreadyExistsException extends CategoryException{

	public CategoryAlreadyExistsException(String message) {
		super(message);
	}
}
