package com.example.demo.config;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	 @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            BufferedReader reader = request.getReader();
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String parsedReq = sb.toString();
            if (parsedReq != null) {
                ObjectMapper mapper = new ObjectMapper();
                AuthReq authReq = mapper.readValue(parsedReq, AuthReq.class);
                return new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalAuthenticationServiceException("Failed to parse authentication request body");
        }
        return null;
    }
	 
    public static class AuthReq {
        String username;
        String password;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
    }
    
    public AuthenticationSuccessHandler successHandler() {
	    return new AuthenticationSuccessHandler() {
	      @Override
	      public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
	        httpServletResponse.getWriter().append("OK");
	        httpServletResponse.setStatus(200);
	      }
	    };
	  }

	  public AuthenticationFailureHandler failureHandler() {
	    return new AuthenticationFailureHandler() {
	      @Override
	      public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
	        httpServletResponse.getWriter().append("Authentication failure");
	        httpServletResponse.setStatus(401);
	      }
	    };
	  }
}
