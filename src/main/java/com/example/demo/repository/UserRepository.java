package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Users;


public interface UserRepository extends MongoRepository<Users, String> {
	Users findByUsername(String username);
	Users findByEmail(String email); 
}
