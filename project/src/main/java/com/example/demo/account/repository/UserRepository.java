package com.example.demo.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.account.model.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{
	@Query(value = "user_id, user_name, email, password, hash_salt, complete from users where user_name =:user_name", nativeQuery = true)
	Users getUsers(String name);
}
