package com.example.demo.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.example.demo.services.MongoUserDetailsService;


@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  MongoUserDetailsService userDetailsService;
  
//  @Bean
//  public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
//      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//      CorsConfiguration config = new CorsConfiguration();
//      config.setAllowCredentials(true);
//      config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:4200"));
//      config.setAllowedMethods(Collections.singletonList("*"));
//      config.setAllowedHeaders(Collections.singletonList("*"));
//      source.registerCorsConfiguration("/**", config);
//      FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
//      bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//      return bean;
//  }
  
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
  	  configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:4200"));
      configuration.setAllowedMethods(Arrays.asList("GET","POST"));
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
  }
  
  
//  //@Override
//  public void addCorsMappings(CorsRegistry registry) {
//      registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
//              .allowedHeaders("*");
//  }
  
  @Bean
  public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
      StrictHttpFirewall firewall = new StrictHttpFirewall();
      firewall.setAllowUrlEncodedSlash(true);
      firewall.setAllowSemicolon(true);
      return firewall;
  }  
 
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	  http.addFilterBefore(new CustomFilter(), ChannelProcessingFilter.class);
	  http
      .csrf().disable().cors().and().authorizeRequests()
      .antMatchers("/").permitAll()
      .antMatchers("/signup").permitAll()
      .antMatchers("/popular").permitAll()
      .antMatchers("/genres").permitAll()
      .antMatchers("/series").permitAll()
      .antMatchers("/search").permitAll()
      .antMatchers("/api/users/profile").permitAll()
      .antMatchers("/login").permitAll()
      .antMatchers("/api/users/register").permitAll()
      .antMatchers("/api/series/create").permitAll()
      .antMatchers("/api/series/publish").permitAll()
      .antMatchers("/api/series/follow").permitAll()
      .antMatchers(
    		  		"/css/**",
    		  		"/js/**",
    		  		"/img/**").permitAll()
      .anyRequest().authenticated()
      .and()
      .formLogin()
      .loginPage("/login")
      .defaultSuccessUrl("/")
      .usernameParameter("username")
      .passwordParameter("password")
      .permitAll()
      //.httpBasic()
      .and().sessionManagement().disable();
  }
  
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Override
  public void configure(AuthenticationManagerBuilder builder) throws Exception {
    builder.userDetailsService(userDetailsService);
  }
}