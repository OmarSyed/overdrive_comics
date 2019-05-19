package com.example.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

//public class CustomSuccessHandler implements AuthenticationSuccessHandler {
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	 @Override
	 public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
             AuthenticationException exception) throws IOException, ServletException {
			response.getOutputStream().write("401 Authentication Failure".getBytes()); 

	 }
	
}
