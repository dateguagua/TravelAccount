package com.example.demo.account.except.countryList;

import com.example.demo.account.except.countryPlan.CountryPlanException;

public class CountryListNotFoundException extends CountryPlanException{

	public CountryListNotFoundException(String message) {
		super(message);
	}
}
