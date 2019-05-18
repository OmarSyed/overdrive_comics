package com.example.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {
	
	 @Override
	 public void onAuthenticationSuccess(HttpServletRequest request,   HttpServletResponse response, Authentication authentication) throws IOException, ServletException  {
		response.getOutputStream().write("my JSON response on success".getBytes()); 
     }

}