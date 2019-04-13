package com.example.demo.config;


import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		//CorsConfiguration config = new CorsConfiguration();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		//config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:4200"));
		response.setHeader("Access-Control-Allow-Headers",
				"authorization, content-type, xsrf-token, Cache-Control, remember-me, WWW-Authenticate");
		response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
		chain.doFilter(request, response);
		
	}

}