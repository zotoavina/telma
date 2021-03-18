package com.mobile.telma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mobile.telma.filter.AuthFilter;

@SpringBootApplication
public class TelmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelmaApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
		FilterRegistrationBean<AuthFilter> filter = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		filter.setFilter(authFilter);
		filter.addUrlPatterns("/api/clients/action");
		return filter;
	}
	
	
	@SuppressWarnings("deprecation")
	@Bean
	   public WebMvcConfigurer corsConfigurer() {
	      return new WebMvcConfigurerAdapter() {
	         @Override
	         public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/api/clients/*").allowedOrigins("http://localhost:8080");
	         }
	      };
	   }

}
