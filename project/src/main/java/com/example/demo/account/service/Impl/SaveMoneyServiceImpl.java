package com.example.demo.account.service.Impl;

import com.example.demo.account.model.dto.SaveMoneyDTO;
import com.example.demo.account.model.entity.SaveMoney;
import com.example.demo.account.model.entity.Users;
import com.example.demo.account.repository.SaveMoneyRepository;
import com.example.demo.account.repository.UserRepository;
import com.example.demo.account.service.SaveMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaveMoneyServiceImpl implements SaveMoneyService {
    
    @Autowired
    private SaveMoneyRepository saveMoneyRepository;
    
    @Autowired
    private UserRepository usersRepository;
    
    @Override
    public List<SaveMoneyDTO> getUserSaveMoneyGoals(Integer userId) {
        System.out.println("=== 查詢用戶存錢目標 ===");
        System.out.println("查詢的 userId: " + userId);
        
        List<SaveMoney> saveMoneyList = saveMoneyRepository.findByUserIdOrderBySaveMoneyDateDesc(userId);
        System.out.println("查詢到的資料筆數: " + saveMoneyList.size());
        
        for (SaveMoney saveMoney : saveMoneyList) {
            System.out.println("資料: ID=" + saveMoney.getSaveMoneyId() + 
                             ", Memo=" + saveMoney.getSaveMoneyMemo() + 
                             ", Goal=" + saveMoney.getSaveMoneyGoal());
        }
        
        List<SaveMoneyDTO> result = saveMoneyList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        System.out.println("轉換後的 DTO 筆數: " + result.size());
        return result;
    }
    
    @Override
    @Transactional
    public SaveMoneyDTO createSaveMoneyGoal(Integer userId, SaveMoneyDTO saveMoneyDTO) {
        System.out.println("=== SaveMoney Service Debug ===");
        System.out.println("接收到的 userId: " + userId);
        System.out.println("接收到的 DTO memo: " + saveMoneyDTO.getSaveMoneyMemo());
        System.out.println("接收到的 DTO goal: " + saveMoneyDTO.getSaveMoneyGoal());
        
        try {
            // 查詢用戶
            Optional<Users> userOpt = usersRepository.findById(userId);
            if (!userOpt.isPresent()) {
                throw new RuntimeException("用戶不存在，userId: " + userId);
            }
            
            Users user = userOpt.get();
            System.out.println("找到的用戶 ID: " + user.getUserId());
            // 不要調用 toString()，會造成無限循環
            
            // 創建 SaveMoney 實體
            SaveMoney saveMoney = new SaveMoney();
            saveMoney.setUser(user);
            saveMoney.setSaveMoneyMemo(saveMoneyDTO.getSaveMoneyMemo());
            saveMoney.setSaveMoneyGoal(saveMoneyDTO.getSaveMoneyGoal());
            saveMoney.setSaveMoneyDate(LocalDate.now());
            
            System.out.println("準備保存 SaveMoney，memo: " + saveMoney.getSaveMoneyMemo());
            System.out.println("準備保存 SaveMoney，goal: " + saveMoney.getSaveMoneyGoal());
            
            // 保存實體
            SaveMoney savedEntity = saveMoneyRepository.save(saveMoney);
            
            System.out.println("保存成功，ID: " + savedEntity.getSaveMoneyId());
            System.out.println("保存後的 user_id: " + (savedEntity.getUser() != null ? savedEntity.getUser().getUserId() : "NULL"));
            
            SaveMoneyDTO result = convertToDTO(savedEntity);
            System.out.println("轉換後的 DTO ID: " + result.getSaveMoneyId());
            System.out.println("=== Debug End ===");
            
            return result;
            
        } catch (Exception e) {
            System.out.println("Service 層錯誤: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("創建存錢目標失敗: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public SaveMoneyDTO updateSaveMoneyGoal(Integer userId, Integer saveMoneyId, SaveMoneyDTO saveMoneyDTO) {
        System.out.println("=== 更新 SaveMoney Debug ===");
        System.out.println("userId: " + userId + ", saveMoneyId: " + saveMoneyId);
        
        try {
            // 查詢要更新的記錄
            Optional<SaveMoney> existingSaveMoneyOpt = saveMoneyRepository.findById(saveMoneyId);
            
            if (!existingSaveMoneyOpt.isPresent()) {
                throw new RuntimeException("存錢目標不存在，ID: " + saveMoneyId);
            }
            
            SaveMoney existingSaveMoney = existingSaveMoneyOpt.get();
            
            // 檢查是否屬於該用戶
            if (!existingSaveMoney.getUser().getUserId().equals(userId)) {
                throw new RuntimeException("無權限修改此目標");
            }
            
            System.out.println("找到的 SaveMoney ID: " + existingSaveMoney.getSaveMoneyId());
            
            // 更新資料
            existingSaveMoney.setSaveMoneyMemo(saveMoneyDTO.getSaveMoneyMemo());
            existingSaveMoney.setSaveMoneyGoal(saveMoneyDTO.getSaveMoneyGoal());
            
            SaveMoney updatedEntity = saveMoneyRepository.save(existingSaveMoney);
            System.out.println("更新成功，ID: " + updatedEntity.getSaveMoneyId());
            
            return convertToDTO(updatedEntity);
            
        } catch (Exception e) {
            System.out.println("更新失敗: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新失敗: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public boolean deleteSaveMoneyGoal(Integer userId, Integer saveMoneyId) {
        System.out.println("=== 刪除 SaveMoney Debug ===");
        System.out.println("userId: " + userId + ", saveMoneyId: " + saveMoneyId);
        
        try {
            // 查詢要刪除的記錄
            Optional<SaveMoney> existingSaveMoneyOpt = saveMoneyRepository.findById(saveMoneyId);
            
            if (!existingSaveMoneyOpt.isPresent()) {
                System.out.println("找不到要刪除的 SaveMoney 記錄");
                return false;
            }
            
            SaveMoney existingSaveMoney = existingSaveMoneyOpt.get();
            
            // 檢查是否屬於該用戶
            if (!existingSaveMoney.getUser().getUserId().equals(userId)) {
                System.out.println("無權限刪除此目標");
                return false;
            }
            
            // 執行刪除
            saveMoneyRepository.delete(existingSaveMoney);
            System.out.println("刪除成功");
            return true;
            
        } catch (Exception e) {
            System.out.println("刪除失敗: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("刪除失敗: " + e.getMessage());
        }
    }
    
    @Override
    public SaveMoneyDTO getSaveMoneyGoalById(Integer userId, Integer saveMoneyId) {
        try {
            Optional<SaveMoney> saveMoneyOpt = saveMoneyRepository.findById(saveMoneyId);
            
            if (!saveMoneyOpt.isPresent()) {
                return null;
            }
            
            SaveMoney saveMoney = saveMoneyOpt.get();
            
            // 檢查是否屬於該用戶
            if (!saveMoney.getUser().getUserId().equals(userId)) {
                return null;
            }
            
            return convertToDTO(saveMoney);
            
        } catch (Exception e) {
            System.out.println("查詢失敗: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public Double getTotalGoalAmount(Integer userId) {
        try {
            Double total = saveMoneyRepository.getTotalGoalAmountByUserId(userId);
            return total != null ? total : 0.0;
        } catch (Exception e) {
            System.out.println("計算總金額失敗: " + e.getMessage());
            return 0.0;
        }
    }
    
    @Override
    public long countUserGoals(Integer userId) {
        try {
            return saveMoneyRepository.countByUserId(userId);
        } catch (Exception e) {
            System.out.println("計算目標數量失敗: " + e.getMessage());
            return 0;
        }
    }
    
    // 轉換實體到DTO
    private SaveMoneyDTO convertToDTO(SaveMoney saveMoney) {
        try {
            SaveMoneyDTO dto = new SaveMoneyDTO();
            dto.setSaveMoneyId(saveMoney.getSaveMoneyId());
            dto.setSaveMoneyMemo(saveMoney.getSaveMoneyMemo());
            dto.setSaveMoneyGoal(saveMoney.getSaveMoneyGoal());
            dto.setSaveMoneyDate(saveMoney.getSaveMoneyDate());
            return dto;
        } catch (Exception e) {
            System.out.println("DTO 轉換失敗: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("DTO 轉換失敗: " + e.getMessage());
        }
    }
}