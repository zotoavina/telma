package com.mobile.telma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.mobile.telma.domains.Client;
import com.mobile.telma.domains.Consommation;
import com.mobile.telma.domains.DataActuel;


@SpringBootApplication
public class TelmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelmaApplication.class, args);

//		Client client = new Client();
//		List<DataActuel> actuels = new ArrayList<>(3);
//		DataActuel da = new DataActuel();
//		da.setQuantite(50);
//		DataActuel da1 = new DataActuel();
//		da1.setQuantite(80);
//		actuels.add(da );
//		actuels.add(da1);
//		client.setDataActuel(actuels);
//		client.setCredit(50);
//		
//		Consommation cons = new Consommation();
//		cons.setQuantite(160);
//		client.consommerData(cons);
//		for(int i = 0; i< cons.getDetails().size(); i++) {
//			System.out.println(cons.getDetails().get(i).getQuantite());
//		}
//		System.out.println(cons.dataRestantAConsommer());
//		System.out.println(client.getCredit());
//		int a = 0; 
		
		
	}
	
//	@Bean
//	public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
//		FilterRegistrationBean<AuthFilter> filter = new FilterRegistrationBean<>();
//		AuthFilter authFilter = new AuthFilter();
//		filter.setFilter(authFilter);
//		filter.addUrlPatterns("/api/clients/action");
//		return filter;
//	}
//	
//	@Bean
//	public FilterRegistrationBean<AdminFilter> filterRegistrationBeanAdmin(){
//		FilterRegistrationBean<AdminFilter> filter = new FilterRegistrationBean<>();
//		AdminFilter authFilter = new AdminFilter();
//		filter.setFilter(authFilter);
//		filter.addUrlPatterns("/admin/*");
//		return filter;
//	}
	
	@SuppressWarnings("deprecation")
	@Bean
	   public WebMvcConfigurer corsConfigurer() {
	      return new WebMvcConfigurerAdapter() {
	         @Override
	         public void addCorsMappings(CorsRegistry registry) {
	             registry.addMapping("/api/clients/*").allowedOrigins("*");
	            // registry.addMapping("admin/*").allowedOrigins("http://localhost:4200").allowedHeaders("*");
	        	 registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").
	        	 allowedOrigins("*").allowedHeaders("*");
	         }
	      };
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
