package com.example.demo.account.repository;

import com.example.demo.account.model.entity.SaveMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveMoneyRepository extends JpaRepository<SaveMoney, Integer> {

    // 使用 JPA 查詢，避免原生 SQL 的問題
    @Query("SELECT s FROM SaveMoney s WHERE s.user.userId = :userId ORDER BY s.saveMoneyDate DESC")
    List<SaveMoney> findByUserIdOrderBySaveMoneyDateDesc(@Param("userId") Integer userId);

    // 計算用戶的總目標金額
    @Query("SELECT SUM(s.saveMoneyGoal) FROM SaveMoney s WHERE s.user.userId = :userId")
    Double getTotalGoalAmountByUserId(@Param("userId") Integer userId);

    // 根據用戶ID統計目標數量
    @Query("SELECT COUNT(s) FROM SaveMoney s WHERE s.user.userId = :userId")
    long countByUserId(@Param("userId") Integer userId);
    
    // 其他方法都使用 JPA 的標準方法，不需要自定義
    // findById, save, delete 等都是繼承自 JpaRepository
}




