package com.example.demo.account.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"countryPlans", "saveMoneyList"}) // 排除所有關聯集合
@Entity
@Table(name = "SaveMoney")
public class SaveMoney {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "save_money_id")
    private Integer saveMoneyId;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users user;
    
    @Column(name = "save_money_memo", length = 255)
    private String saveMoneyMemo;
    
    @Column(name = "save_money_goal")
    private Double saveMoneyGoal;
    
    @Column(name = "save_money_date")
    private LocalDate saveMoneyDate;
}
