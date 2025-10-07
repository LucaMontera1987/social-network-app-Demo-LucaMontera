package com.restController;

import java.time.Duration;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.loginDto.loginRequestDto;
import com.dto.token.TokenAccess;
import com.model.User;
import com.security.JwtService;
import com.service.UserService;


@RestController
public class Login {
	
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final JwtService jwtService;
	private final JwtDecoder jwtDecoder;
	
	
	



	public Login(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService,
			JwtDecoder jwtDecoder) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtService = jwtService;
		this.jwtDecoder = jwtDecoder;
	}



	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping("/login")
	public ResponseEntity<TokenAccess> login(@RequestBody loginRequestDto loginDto) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));

		List<String> ruoloTmp = auth.getAuthorities().stream().map(t -> t.getAuthority())
				.map(t -> t.replace("ROLE_", "")).toList();

		String tokenAccess = jwtService.generateToken(auth.getName(), ruoloTmp);
		String refreshToken = jwtService.generateRefreshToken(auth.getName());
		
		 ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
	                .httpOnly(true)
	                .secure(true) //in produzione https true
	                .path("/")
	                .maxAge(Duration.ofDays(7))
	                .sameSite("None")
	                .build();

		 TokenAccess response = new TokenAccess(tokenAccess);
		 
		  System.out.println("✅ LOGIN riuscito");
	        System.out.println("Token Access: " + tokenAccess);
	        System.out.println("Refresh Token: " + refreshToken);
	        System.out.println("Set-Cookie header: " + cookie.toString());
	            return ResponseEntity.ok()
	                .header(HttpHeaders.SET_COOKIE, cookie.toString())
	                .body(response);

	}
	
	
	
	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String tokenRefreshRequest) {
	 
	    
		if (tokenRefreshRequest == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing refresh token");
	    }

	    try {
	        boolean valid = jwtService.validateToken(tokenRefreshRequest);
	        if (!valid) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
	        }

	        String username = jwtService.extractUsername(tokenRefreshRequest);
	        Jwt jwt = jwtDecoder.decode(tokenRefreshRequest);

	        
	        
	        if (!"refresh".equals(jwt.getClaimAsString("type"))) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token type");
	        }

	        User utente = userService.findByEmail(username);
	        List<String> ruoloTmp = List.of( utente.getRoles().toString());

	        String accessToken = jwtService.generateToken(username, ruoloTmp);
	        String newRefreshToken = jwtService.generateRefreshToken(username);

	        ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
	                .httpOnly(true)
	                .secure(true)
	                .path("/")
	                .maxAge(Duration.ofDays(7))
	                .sameSite("None")
	                .build();

	        TokenAccess accessDto = new TokenAccess(accessToken);
	     
	        
	        return ResponseEntity.ok()
	                .header(HttpHeaders.SET_COOKIE, cookie.toString())
	                .body(accessDto);

	    } catch (JwtException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
	    }
	}
       	


    			
	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping("/deleteRefreshToken")
	public ResponseEntity<?> deleteRefreshToken(@CookieValue(name = "refreshToken", required = false) String tokenRefreshRequest) {
	    

	        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
	                .httpOnly(true)
	                .secure(true)
	                .path("/")
	                .maxAge(0)
	                .sameSite("None")
	                .build();

	     
	        
	        return ResponseEntity.ok()
	                .header(HttpHeaders.SET_COOKIE, cookie.toString())
	                .body("");

	    
	}


}
