package com.example.demo.account.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModdelMapperConfig {
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
