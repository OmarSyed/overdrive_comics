package com.example.demo.controllers;


import java.io.BufferedOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject.Type.BsonType;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.MediaType;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.ComicSeries;
//import com.example.demo.Users;
import com.example.demo.entity.Users;
import com.example.demo.repository.SeriesRepository;
import com.example.demo.repository.UserRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;


@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UsersController {
	
	@Autowired
	private UserRepository repository;
	//private MongoUserDetailsService service;
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private SeriesRepository seriesrepository;
	@Autowired
	GridFsOperations gridOperations;
	
	public static String curUser="";

	
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
			List<String> liked = new ArrayList<String>();
			List<String> editorPics = new ArrayList<String>();
			user.setFollowedSeries(followed);
			user.setProducedSeries(produced);
			user.setLikedChapters(liked);
			user.setEditorPics(editorPics);;
			repository.save(user);
			//service.save(user);
			System.out.println("sucess");
			return "success";
		}	
	}
	

	//@CrossOrigin
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public Users showUser(@CookieValue("username") String username) {
		//System.out.println("its in profile endpoint");
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//System.out.println(auth.getName());
		//System.out.println(curUser);
		return repository.findByUsername(username); 
	}
	
	@RequestMapping(value="/profile/bio", method = RequestMethod.POST)
	public boolean editBio(@Valid @RequestBody Users user, @CookieValue("username") String username) {
		Users change = repository.findByUsername(username);
		change.setBio(user.getBio());
		repository.save(change);
		return true;
	}
	
	@RequestMapping(value="/profile/editorPics", method = RequestMethod.GET)
	public List<String> getUserEditorPics(@CookieValue("username") String username) {
		Users user = repository.findByUsername(username);
		if (!user.getEditorPics().isEmpty())
			return user.getEditorPics();
		return null;
	}
	
	@RequestMapping(value="/profile/username", method = RequestMethod.POST)
	public boolean editUsername(@Valid @RequestBody Users user, @CookieValue("username") String username) throws NullPointerException{
		Users check = repository.findByUsername(username);
		List<ComicSeries> series = seriesrepository.findByAuthor(username);
		if(repository.findByUsername(user.getUsername())==null) {
			check.setUsername(user.getUsername());
			repository.save(check);
			for(int i = 0; i<series.size(); i++) {
				series.get(i).setAuthor(user.getUsername());
				seriesrepository.save(series.get(i));
			}
			curUser = user.getUsername();
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping(value="/profile/password", method = RequestMethod.POST)
	public boolean editPassword(@Valid @RequestBody Users user, @CookieValue("username") String username) {
		Users check = repository.findByUsername(username);
		check.setPassword(user.getPassword());
		repository.save(check);
		return true;
	}
	
	@RequestMapping(value="/profile/email", method = RequestMethod.POST)
	public boolean editEmail(@Valid @RequestBody Users user, @CookieValue("username") String username) {
		Users check = repository.findByUsername(username);
		if(repository.findByEmail(user.getEmail())==null) {
			//System.out.println(user.getEmail());
			check.setEmail(user.getEmail());
			repository.save(check);
			return true;
		}else {
			return false;
		}
	}
	
	@CrossOrigin
	@RequestMapping(value="/profile/pic", method = RequestMethod.POST)
	public boolean editPic(@RequestParam("pic") MultipartFile imagefile, @CookieValue("username") String username) throws IllegalStateException, IOException {
		Users user = repository.findByUsername(username); 
		System.out.println(username);
		String message = "";
		String filename = "";
        //MultipartFile file = imagefile;
        try {
            byte[] bytes = imagefile.getBytes();

            // Creating the directory to store file
            //String rootPath = System.getProperty("catalina.home");
            File dir = new File("../" + "overdrive_frontend/src/assets/" + username);
            if (!dir.exists())
                dir.mkdirs();
            filename = "assets/" + username +"/" + "image0.png";
            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath()
                    + File.separator + "image0.png");
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

//            logger.info("Server File Location="
//                    + serverFile.getAbsolutePath());

            message = message + "You successfully uploaded file=" + "image"
                    + "<br />";
        } catch (Exception e) {
            return false;
        }
        System.out.println(filename);
        user.setProfilePic(filename);
        repository.save(user);
        return true;
	}
	
	@RequestMapping(value = "/validUser", method=RequestMethod.POST)
	public boolean changePassword(@RequestBody Users user) {
		Users userr = repository.findByUsername(user.getUsername()); 
		if (userr != null)
			return true; 
		else
			return false; 
	}
	
	@RequestMapping(value="/checkAnswer", method=RequestMethod.POST)
	public boolean checkAnswer(@Valid @RequestBody Users user) {
		Users userr = repository.findByUsername(user.getUsername()); 
		if (user.getSecurityAnswer().equals(userr.getSecurityAnswer()))
			return true; 
		else
			return false; 
	}
	
		
	
	
}
