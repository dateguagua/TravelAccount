package com.example.demo.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.account.model.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    
    // 你原有的方法
    @Query(value = " select user_id, user_name, email, password, hash_salt, complete from users where user_name =:userName", nativeQuery = true)
    Users getUsers(@Param("userName") String name);
    
    boolean existsByUserName(String name);
    
    /**
     * 根據用戶名查找用戶（新增方法）
     * 使用 JPA 方法命名規則，更簡潔
     */
    Optional<Users> findByUserName(String userName);
}