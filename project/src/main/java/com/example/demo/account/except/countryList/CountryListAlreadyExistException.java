package com.example.demo.account.except.countryList;

public class CountryListAlreadyExistException extends CountryListException{

	public CountryListAlreadyExistException(String message) {
		super(message);
	}
}
