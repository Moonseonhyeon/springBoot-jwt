package com.cos.securityex01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.securityex01.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	
	
}
