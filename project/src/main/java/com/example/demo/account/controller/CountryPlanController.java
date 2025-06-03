package com.example.demo.account.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/countryPlan"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8085"}, allowCredentials = "true")
public class CountryPlanController {

}
