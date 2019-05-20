package com.example.demo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("api/login")
public class LoginController {
	
	@Autowired
	UserRepository userRepo;
	
	@Bean
	public SecurityContextHolder securityContextHolder() {
		return new SecurityContextHolder();
	}

	@RequestMapping(value = "/loginuser", method = RequestMethod.POST)
	public Users login(@Valid @RequestBody Users user) {
		Users foundUser = userRepo.findByUsername(user.getUsername());
		if(foundUser != null) {
			System.out.println("found user");
			if(foundUser.getPassword().equals(user.getPassword())) {
				System.out.println("passwords match");
				Authentication auth = new UsernamePasswordAuthenticationToken(foundUser.getUsername(), foundUser.getPassword());
				SecurityContextHolder securityContext = securityContextHolder();
				securityContext.getContext().setAuthentication(auth);
				return foundUser;
			} else {
				System.out.println("passwords don't match");
				return null;
			}
		} else {
			System.out.println("User not found");
			return null;
		}
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		SecurityContextHolder sc = securityContextHolder();
		sc.clearContext();
		return "Success";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String getUser() {
		return (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}