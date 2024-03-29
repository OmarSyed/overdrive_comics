package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	SecurityContextHolder secHolder;
	
	@Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
  
            // use the credentials
            // and authenticate against the third-party system
            Authentication tmp = new UsernamePasswordAuthenticationToken(name, password);
            authentication.setAuthenticated(false);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            tmp.setAuthenticated(false);
            return tmp;
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
          UsernamePasswordAuthenticationToken.class);
    }
}
