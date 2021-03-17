package com.mobile.telma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

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
		filter.addUrlPatterns("/api/categories/*");
		return filter;
	}

}
