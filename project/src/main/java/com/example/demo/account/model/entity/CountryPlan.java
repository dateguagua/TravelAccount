package com.example.demo.account.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@ToString(exclude = "journeys")
@EqualsAndHashCode(exclude = "journeys")

@Table(name = "country_plan")
public class CountryPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "countryplan_id")
	private Integer countryPlanId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")  // 對應到 users 表
	private Users user;
	
	@ManyToOne(fetch = FetchType.LAZY)  // 不要加 cascade
	@JoinColumn(name = "country_id")
	private CountryList countryList;
	
	@Column(name = "total_days")
	private Integer totalDays;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_time")
	private LocalDate startTime;
	
	@OneToMany(mappedBy = "countryPlan", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Journey> journeys = new ArrayList<>();
	
	@OneToMany(mappedBy = "countryPlan", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TravelMoney> travelMoneys = new ArrayList<>();
}
