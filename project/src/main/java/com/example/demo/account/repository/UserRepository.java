package com.example.demo.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.account.model.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{
	
	//取得使用者
	@Query(value = " select user_id, user_name, email, password, hash_salt, complete from users where user_name =:userName", nativeQuery = true)
	Users getUsers(@Param("userName") String name);
}
