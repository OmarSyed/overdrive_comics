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
}
