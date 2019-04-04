package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@RequestMapping("/popular")
	public String popular() {
		return "popular";
	}
	
	@RequestMapping("/series")
	public String series() {
		return "series";
	}
	
	@RequestMapping("/create")
	public String create() {
		return "create";
	}
	
	@RequestMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}
	
	@RequestMapping("/discover")
	public String discover() {
		return "discover";
	}
	
	@RequestMapping("/editor")
	public String editor() {
		return "editor";
	}
	
	@RequestMapping("/genres")
	public String genres() {
		return "genres";
	}
	
	@RequestMapping("/profile")
	public String profile() {
		return "profile";
	}
	
	@RequestMapping("/search")
	public String search() {
		return "search";
	}
	
	@RequestMapping("/settings")
	public String settings() {
		return "settings";
	}
	
	@RequestMapping("/signup")
	public String signup() {
		return "signup";
	}
	/*
	 * Below list of css files 
	 * that will be served
	 */
	@RequestMapping("/maincss")
	public String mainCSS() {
		return "./css/main.css";
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
