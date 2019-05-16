package com.example.demo.config;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	 public AuthenticationSuccessHandler() {
	       super();
	       setRedirectStrategy(new NoRedirectStrategy());
	   }

	    @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	            Authentication authentication) throws IOException, ServletException {

	           super.onAuthenticationSuccess(request, response, authentication);
//	           response.write("my JSON response");
	           response.getOutputStream().write("my JSON response".getBytes());
	    }


	    protected class NoRedirectStrategy implements RedirectStrategy {

	        @Override
	        public void sendRedirect(HttpServletRequest request,
	                HttpServletResponse response, String url) throws IOException {
	            // no redirect

	        }

	    }
}
