package com.example.demo.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject.Type.BsonType;
import org.springframework.http.MediaType;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.ComicSeries;
//import com.example.demo.Users;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
//import com.example.demo.services.MongoUserDetailsService;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UsersController {
	
	@Autowired
	private UserRepository repository;
	//private MongoUserDetailsService service;
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public static String curUser;

	
	public static String getCurUser() {
		return curUser;
	}

	public void setCurUser(String curUser) {
		UsersController.curUser = curUser;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String createUser(@Valid @RequestBody Users user) {
		//Users grr = new Users("sdfs", "sdfsfwe");
		//service.saveUser(grr);
		//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		if(repository.findByUsername(user.getUsername())!= null) {
			return "Duplicate";
		}else if(repository.findByEmail(user.getEmail())!= null){
			return "Duplicate";
		}else {
			//repository.save(user);
			//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			List<String> followed = new ArrayList<String>();
			List<String> produced = new ArrayList<String>();
			user.setFollowedSeries(followed);
			user.setProducedSeries(produced);
			repository.save(user);
			//service.save(user);
			System.out.println("sucess");
			return "success";
		}	
	}
	
	//@CrossOrigin
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String loginUser(@Valid @RequestBody Users user) {
		if(repository.findByUsername(user.getUsername())!=null) {
			Users check = repository.findByUsername(user.getUsername());
			if(check.getUsername().equals(user.getUsername()) && check.getPassword().equals(user.getPassword())) {
				curUser = user.getUsername();
				setCurUser(curUser);
				//System.out.println(curUser);
				return curUser;
			}
			return "failure";
		}else {
			return "failure";
		}
	}
	
	@RequestMapping(value ="/logout", method=RequestMethod.GET)
	public String logoutUser() {
		UsersController.curUser = "";
		return "success";
	}
	
	//@CrossOrigin
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public Users showUser() {
		//System.out.println("its in profile endpoint");
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//System.out.println(auth.getName());
		//System.out.println(curUser);
		return repository.findByUsername(curUser); 
	}
	
	@RequestMapping(value="/profile/bio", method = RequestMethod.POST)
	public Users editBio(@Valid @RequestBody Users user) {
		Users change = repository.findByUsername(user.getUsername());
		change.setBio(user.getBio());
		repository.save(change);
		return change;
	}
	
	@RequestMapping(value="/profile/username", method = RequestMethod.POST)
	public String editUsername(@Valid @RequestBody Users user) {
		Users check = repository.findByUsername(curUser);
		if(repository.findByUsername(user.getUsername())==null) {
			check.setUsername(user.getUsername());
			repository.save(check);
			return "success";
		}else {
			return "duplicate";
		}
	}
	
	@RequestMapping(value="/profile/password", method = RequestMethod.POST)
	public Users editPassword(@Valid @RequestBody Users user) {
		Users check = repository.findByUsername(user.getUsername());
		check.setPassword(user.getPassword());
		repository.save(check);
		return check;
	}
	
	@RequestMapping(value="/profile/email", method = RequestMethod.POST)
	public String editEmail(@Valid @RequestBody Users user) {
		Users check = repository.findByUsername(curUser);
		if(repository.findByEmail(user.getEmail())==null) {
			//System.out.println(user.getEmail());
			check.setEmail(user.getEmail());
			repository.save(check);
			return "success";
		}else {
			return "duplicate";
		}
	}
	
//	@RequestMapping(value="/profile/pic", method = RequestMethod.POST)
//	public Users editProfilePic(@ModelAttribute("userFormData") LoginDTO fromData, BindingResult result) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		Users user = repository.findByUsername(auth.getName());
//		user.setProfilePic(formData.get("profile_pic"));
//	}
	
	
	
	
	
//	@RequestMapping(value = "/getuser", method = RequestMethod.POST)
//	public String getUser(@Valid @RequestBody Users user){
//		Users check = repository.findByUsername(user.getUsername());
//		//Users check  = repository.findByUsername(user.getUsername());
//		//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//		if (check == null)
//			
//		UserDetails test  = service.loadUserByUsername(user.getUsername());
//		if(repository.findByUsername(user.getUsername())!= null) {
//			return "success";
//		}else {
//			return "error";
//		}
//	}
	
	
	
	
	
	
	
	
}
