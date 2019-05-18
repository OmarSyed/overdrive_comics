package com.example.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.demo.config.CustomFilter;

@Configuration

@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	@Override
    protected void configure(
      AuthenticationManagerBuilder auth) throws Exception {
  
        auth.authenticationProvider(authProvider());
    }
	
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	http.addFilterBefore(new CustomFilter(), ChannelProcessingFilter.class);
	http
    .csrf().disable()
    .exceptionHandling()
    .and()
    .authorizeRequests()
    .antMatchers("/api/foos").authenticated()
    .and()
    .anonymous()
    .disable()
    .formLogin()
    	.successHandler(successHandler())
    	.failureHandler(failureHandler())
    .and()
    .logout();
  }
  
//  @Bean 
//  public SecurityContextHolder securityContextHolder() {
//	  return new SecurityContextHolder();
//  }
  
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public CustomAuthenticationProvider authProvider() {
	  return new CustomAuthenticationProvider();
  }
  
  @Bean
  public CustomSuccessHandler successHandler() {
	  return new CustomSuccessHandler();
  }
  
//  @Override
//  public void configure(AuthenticationManagerBuilder builder) throws Exception {
//    builder.userDetailsService(userDetailsService);
//  }
  
//  private AuthenticationSuccessHandler successHandler() {
//	    return new AuthenticationSuccessHandler() {
//	      @Override
//	      public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//	        httpServletResponse.getWriter().append("OK");
//	        httpServletResponse.setStatus(200);
//	      }
//	    };
//	  }

	  private AuthenticationFailureHandler failureHandler() {
	    return new AuthenticationFailureHandler() {
	      @Override
	      public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
	        httpServletResponse.getWriter().append("Authentication failure");
	        httpServletResponse.setStatus(401);
	      }
	    };
	  }
  
}