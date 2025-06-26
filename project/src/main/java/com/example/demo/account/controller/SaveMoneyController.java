package com.example.demo.account.controller;

import com.example.demo.account.model.dto.SaveMoneyDTO;
import com.example.demo.account.service.SaveMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/saveMoney")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8085"}, allowCredentials = "true")
public class SaveMoneyController {
    
    @Autowired
    private SaveMoneyService saveMoneyService;
    
 // 查詢用戶所有存錢目標
    @GetMapping
    public ResponseEntity<?> getUserSaveMoneyGoals(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        
        // 暫時的測試用 userId
        if (userId == null) {
            System.out.println("Session 中沒有 userId，使用測試用 userId: 4");
            userId = 4; // 根據你的資料庫，這裡用 4 (因為你的資料顯示 user_id = 4)
        }
        
        try {
            List<SaveMoneyDTO> goals = saveMoneyService.getUserSaveMoneyGoals(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("goals", goals);
            response.put("totalAmount", saveMoneyService.getTotalGoalAmount(userId));
            response.put("goalCount", saveMoneyService.countUserGoals(userId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // 印出完整錯誤
            return ResponseEntity.status(500).body("查詢失敗：" + e.getMessage());
        }
    }

    // 新增存錢目標
    @PostMapping
    public ResponseEntity<?> createSaveMoneyGoal(@RequestBody SaveMoneyDTO saveMoneyDTO, HttpSession session) {
        System.out.println("=== Controller Debug ===");
        System.out.println("Session ID: " + session.getId());
        
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("從 Session 獲取的 userId: " + userId);
        
        // 暫時的測試用 userId
        if (userId == null) {
            System.out.println("Session 中沒有 userId，使用測試用 userId: 4");
            userId = 4; // 根據你的資料庫，這裡用 4
        }
        
        System.out.println("使用的 userId: " + userId);
        System.out.println("接收到的 SaveMoneyDTO: " + saveMoneyDTO);
        
        try {
            SaveMoneyDTO createdGoal = saveMoneyService.createSaveMoneyGoal(userId, saveMoneyDTO);
            System.out.println("創建成功，回傳: " + createdGoal);
            return ResponseEntity.ok(createdGoal);
        } catch (Exception e) {
            System.out.println("創建失敗，錯誤: " + e.getMessage());
            e.printStackTrace(); // 印出完整錯誤堆疊
            return ResponseEntity.status(500).body("新增失敗：" + e.getMessage());
        }
    }

    // 更新存錢目標
    @PutMapping("/{saveMoneyId}")
    public ResponseEntity<?> updateSaveMoneyGoal(
            @PathVariable Integer saveMoneyId,
            @RequestBody SaveMoneyDTO saveMoneyDTO,
            HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        
        // 暫時的測試用 userId
        if (userId == null) {
            userId = 4;
        }
        
        try {
            SaveMoneyDTO updatedGoal = saveMoneyService.updateSaveMoneyGoal(userId, saveMoneyId, saveMoneyDTO);
            return ResponseEntity.ok(updatedGoal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // 印出完整錯誤
            return ResponseEntity.status(500).body("更新失敗：" + e.getMessage());
        }
    }

    // 刪除存錢目標
    @DeleteMapping("/{saveMoneyId}")
    public ResponseEntity<?> deleteSaveMoneyGoal(@PathVariable Integer saveMoneyId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        
        // 暫時的測試用 userId
        if (userId == null) {
            userId = 4;
        }
        
        try {
            boolean deleted = saveMoneyService.deleteSaveMoneyGoal(userId, saveMoneyId);
            if (deleted) {
                return ResponseEntity.ok("刪除成功");
            } else {
                return ResponseEntity.status(404).body("目標不存在或無權限刪除");
            }
        } catch (Exception e) {
            e.printStackTrace(); // 印出完整錯誤
            return ResponseEntity.status(500).body("刪除失敗：" + e.getMessage());
        }
    }
    // 查詢特定存錢目標
    @GetMapping("/{saveMoneyId}")
    public ResponseEntity<?> getSaveMoneyGoalById(@PathVariable Integer saveMoneyId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("請先登入");
        }
        
        try {
            SaveMoneyDTO goal = saveMoneyService.getSaveMoneyGoalById(userId, saveMoneyId);
            if (goal != null) {
                return ResponseEntity.ok(goal);
            } else {
                return ResponseEntity.status(404).body("目標不存在");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("查詢失敗：" + e.getMessage());
        }
    }
    
    // 獲取統計資訊
    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("請先登入");
        }
        
        try {
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalAmount", saveMoneyService.getTotalGoalAmount(userId));
            statistics.put("goalCount", saveMoneyService.countUserGoals(userId));
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("查詢統計失敗：" + e.getMessage());
        }
    }
}