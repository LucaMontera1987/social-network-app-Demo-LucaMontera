package com.security;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {

		// 1. Regole CORS
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:4200"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);

		// 2. Sorgente che applica le regole a tutte le rotte
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		// 3. Istanza del filtro CORS
		CorsFilter corsFilter = new CorsFilter(source);

		// 4. Registrazione con priorità massima
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(corsFilter);
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // eseguito prima di Spring Security

		return bean;
	}

}
