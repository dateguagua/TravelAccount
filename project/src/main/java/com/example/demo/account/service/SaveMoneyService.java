package com.example.demo.account.service;


import com.example.demo.account.model.dto.SaveMoneyDTO;
import java.util.List;

public interface SaveMoneyService {
    
    // 查詢用戶所有存錢目標
    List<SaveMoneyDTO> getUserSaveMoneyGoals(Integer userId);
    
    // 新增存錢目標
    SaveMoneyDTO createSaveMoneyGoal(Integer userId, SaveMoneyDTO saveMoneyDTO);
    
    // 更新存錢目標
    SaveMoneyDTO updateSaveMoneyGoal(Integer userId, Integer saveMoneyId, SaveMoneyDTO saveMoneyDTO);
    
    // 刪除存錢目標
    boolean deleteSaveMoneyGoal(Integer userId, Integer saveMoneyId);
    
    // 根據ID查詢特定目標
    SaveMoneyDTO getSaveMoneyGoalById(Integer userId, Integer saveMoneyId);
    
    // 計算用戶總目標金額
    Double getTotalGoalAmount(Integer userId);
    
    // 統計用戶目標數量
    long countUserGoals(Integer userId);
}