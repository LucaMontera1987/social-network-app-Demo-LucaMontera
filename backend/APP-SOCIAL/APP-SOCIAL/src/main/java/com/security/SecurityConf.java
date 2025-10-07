package com.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConf {
	

		public SecurityConf() {
			System.out.println("dentro il filterCHAIN");
		}

		@Bean
		public WebSecurityCustomizer webSecurityCustomizer() {
			return (web) -> web.ignoring().requestMatchers(HttpMethod.OPTIONS, "/**");
		}

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			System.out.println("filterCHAIN");
			http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())

					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

					.authorizeHttpRequests(auth -> auth
							.requestMatchers("/register/registerUser").permitAll()
						
							
							.requestMatchers("/refreshToken").permitAll()
							.requestMatchers("/login").permitAll()
							
							
							
							.requestMatchers("/test-chat.html").permitAll()
							.requestMatchers("/*.html").permitAll()

							.requestMatchers("/favicon.ico/**").permitAll() // evita il 401 sul favicon
							.requestMatchers("/ws/**").permitAll()

							.anyRequest().authenticated())
					.oauth2ResourceServer(
							oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

			return http.build();
		}

		@Bean
		public AuthenticationManager authenticationManager(UserDetailService userDetailService,
				PasswordEncoder passwordEncoder) {
			System.out.println("dentro authenticationManager");

			DaoAuthenticationProvider authentication = new DaoAuthenticationProvider();
			authentication.setUserDetailsService(userDetailService);
			authentication.setPasswordEncoder(passwordEncoder);

			return new ProviderManager(authentication);

		}

		@Bean
		public JwtAuthenticationConverter jwtAuthenticationConverter() {
			JwtGrantedAuthoritiesConverter converterRole = new JwtGrantedAuthoritiesConverter();

			converterRole.setAuthorityPrefix("ROLE_");
			converterRole.setAuthoritiesClaimName("role");
			System.out.println(converterRole.getClass());

			JwtAuthenticationConverter jwtconverter = new JwtAuthenticationConverter();
			jwtconverter.setJwtGrantedAuthoritiesConverter(converterRole);
			return jwtconverter;
		}

		@Bean
		public CorsConfigurationSource corsConfigurationSource() {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(List.of("http://localhost:4200")); // ✅ frontend
			config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
			config.setAllowedHeaders(List.of("*"));
			config.setAllowCredentials(true); // Se usi cookie o sessioni
			config.setExposedHeaders(List.of("Authorization"));

			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", config); // Applica su tutte le rotte
			return source;
		}

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}


}
