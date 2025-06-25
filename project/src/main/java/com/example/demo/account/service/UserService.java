package com.example.demo.account.service;

import java.util.List;

import com.example.demo.account.model.dto.UsersDTO;
import com.example.demo.account.model.entity.Users;

public interface UserService {
    
    // 原有方法
    List<UsersDTO> findAllUser();
    void addUser(String userName, String password);
    
    // 新增方法
    /**
     * 根據用戶名查找用戶實體
     */
    Users findUserByUserName(String userName);
    
    /**
     * 根據用戶ID查找用戶實體
     */
    Users findUserById(Integer userId);
}