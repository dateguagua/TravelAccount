package com.example.demo.account.model.dto;

import java.time.LocalDateTime;

import com.example.demo.account.model.entity.Journey;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "journey")
public class JourneyDTO {

	private Integer journeyId;
	//private Integer countryPlanId;
	private String location;
	private String attraction;
	private String memo;
	private LocalDateTime time;
	private Integer days;
	//private Boolean isDelete;
}
