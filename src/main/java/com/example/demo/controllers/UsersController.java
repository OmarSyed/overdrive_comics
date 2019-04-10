package com.example.demo.controllers;

import java.util.ArrayList;



import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

//import com.example.demo.Users;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.MongoUserDetailsService;


@RestController
@RequestMapping("/api")
public class UsersController {
	
	@Autowired
	private UserRepository repository;
	private MongoUserDetailsService service;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String createUser(@Valid @RequestBody Users user) {
		//Users grr = new Users("sdfs", "sdfsfwe");
		//service.saveUser(grr);
		if(repository.findByUsername(user.getUsername())!= null) {
			return "Duplicate";
		}else {
			//repository.save(user);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			repository.save(user);
			//service.save(user);
			return "success";
		}	
	}
	
//	@RequestMapping(value = "/getuser", method = RequestMethod.POST)
//	public String getUser(@Valid @RequestBody Users user){
//		Users check = repository.findByUsername(user.getUsername());
//		//Users check  = repository.findByUsername(user.getUsername());
//		//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//		
//		UserDetails test  = service.loadUserByUsername(user.getUsername());
//		if(repository.findByUsername(user.getUsername())!= null) {
//			return "success";
//		}else {
//			return "error";
//		}
//	}
//	
	
	
	
	
	
	
	
}
