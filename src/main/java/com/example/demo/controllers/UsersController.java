package com.example.demo.controllers;

import java.util.ArrayList;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.MongoUserDetailsService;


@RestController
@RequestMapping("/api")
public class UsersController {
	
	@Autowired
	private UserRepository repository;
	private MongoUserDetailsService service;
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String createUser(@Valid @RequestBody Users user) {
		//Users grr = new Users("sdfs", "sdfsfwe");
		//service.saveUser(grr);
		System.out.print("test");
		if(repository.findByUsername(user.getUsername())!= null) {
			return "Duplicate";
		}else {
			//repository.save(user);
			service.saveUser(user);
			return "success";
		}
	}
	
	
	
	
	
	
}
