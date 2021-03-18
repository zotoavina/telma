package com.mobile.telma;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mobile.telma.filter.AdminFilter;
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
	
	@Bean
	public FilterRegistrationBean<AdminFilter> filterRegistrationBeanAdmin(){
		FilterRegistrationBean<AdminFilter> filter = new FilterRegistrationBean<>();
		AdminFilter authFilter = new AdminFilter();
		filter.setFilter(authFilter);
		filter.addUrlPatterns("/admin/*");
		return filter;
	}
	
	
	
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() { 
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	
}
