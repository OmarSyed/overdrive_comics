package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

@SpringBootApplication
public class Overdrive308Application {
	
	@Autowired
	private static UserRepository user; 
	
	
	public static void main(String[] args) {
		SpringApplication.run(Overdrive308Application.class, args);
		
	}
	
//	@Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                String urls = env.getProperty("cors.urls");
//                CorsRegistration reg = registry.addMapping("/api/**");
//                for(String url: urls.split(",")) {
//                    reg.allowedOrigins(url);
//                }
//            }
//        };
//    }    
	
//	@Bean
//	  public ViewResolver viewResolver() {
//	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//	    templateResolver.setTemplateMode("XHTML");
//	    templateResolver.setPrefix("views/");
//	    templateResolver.setSuffix(".html");
//
//	    SpringTemplateEngine engine = new SpringTemplateEngine();
//	    engine.setTemplateResolver(templateResolver);
//
//	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//	    viewResolver.setTemplateEngine(engine);
//	    return viewResolver;
//	  }
}
