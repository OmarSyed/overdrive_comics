package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.services.MongoUserDetailsService;

@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  MongoUserDetailsService userDetailsService;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable().authorizeRequests()
      .antMatchers("/").permitAll()
      .antMatchers("/signup").permitAll()
      .antMatchers("/popular").permitAll()
      .antMatchers("/genres").permitAll()
      .antMatchers("/series").permitAll()
      .antMatchers("/search").permitAll()
      .antMatchers("/profile").permitAll()
      .antMatchers(
    		  		"/css/**",
    		  		"/js/**",
    		  		"/img/**").permitAll()
      .anyRequest().authenticated()
      .and()
      .formLogin()
      .loginPage("/signup")
      .defaultSuccessUrl("/")
      .usernameParameter("username")
      .passwordParameter("password")
      .permitAll();
      //.httpBasic()
      //.and().sessionManagement().disable();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Override
  public void configure(AuthenticationManagerBuilder builder) throws Exception {
    builder.userDetailsService(userDetailsService);
  }
}