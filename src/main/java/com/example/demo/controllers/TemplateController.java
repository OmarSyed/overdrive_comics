package com.example.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class TemplateController {
	
	@RequestMapping("/")
	public String home() {
		return "views/index";
	}
	
	@RequestMapping("/popular")
	public String popular() {
		return "views/popular";
	}
	
	@RequestMapping("/series")
	public String series() {
		return "views/series";
	}
	
	@RequestMapping("/create")
	public String create() {
		return "views/create";
	}
	
	@RequestMapping("/dashboard")
	public String dashboard() {
		return "views/dashboard";
	}
	
	@RequestMapping("/discover")
	public String discover() {
		return "views/discover";
	}
	
	@RequestMapping("/editor")
	public String editor() {
		return "views/editor";
	}
	
	@RequestMapping("/genres")
	public String genres() {
		return "views/genres";
	}
	
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public String showUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName(); 
	}
	
	@RequestMapping("/search")
	public String search() {
		return "views/search";
	}
	
	@RequestMapping("/settings")
	public String settings() {
		return "views/settings";
	}
	
	@RequestMapping("/signup")
	public String signup() {
		return "views/signup";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "views/login";
	}
	/*
	 * Below list of css files 
	 * that will be served
	 */
	@RequestMapping( value= "/maincss")
	public String mainCSS() {
		return "./static/css/main.css";
	}
	
	@RequestMapping("/literallycanvascss")
	public String literallyCanvasCSS() {
		return "./js/canvas/css/literallycanvas.css";
	}
	
	/*
	 * For JS files that will be served
	 */
	@RequestMapping("/mainjs")
	public String mainJS() {
		return "./js/main.js";
	}
	
	@RequestMapping("/literallycanvasjs")
	public String literallyCanvasJS() {
		return "./js/canvas/js/literallycanvas.min.js";
	}
}
