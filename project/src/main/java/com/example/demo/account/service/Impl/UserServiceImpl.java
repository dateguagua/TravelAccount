package com.example.demo.account.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.account.model.dto.UsersDTO;
import com.example.demo.account.model.entity.Users;
import com.example.demo.account.repository.UserRepository;
import com.example.demo.account.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // 原有方法實作...
    @Override
    public List<UsersDTO> findAllUser() {
        // 你的原有實作
        return null; // 請填入你的實作
    }
    
    @Override
    public void addUser(String userName, String password) {
        // 你的原有實作
    }
    
    // 新增方法實作
    @Override
    public Users findUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("找不到用戶：" + userName));
    }
    
    @Override
    public Users findUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("找不到用戶：" + userId));
    }
}