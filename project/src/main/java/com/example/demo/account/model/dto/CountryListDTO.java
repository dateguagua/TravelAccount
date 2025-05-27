package com.example.demo.account.model.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Country_List")
public class CountryListDTO {

	private Integer countryId;
	private String country;
}
