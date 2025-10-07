package com.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.model.User;
import com.service.UserService;

@Service
public class JwtService {
	
	private final UserService userService;
	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;
	
	

	public JwtService(UserService userService, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
		this.userService = userService;
		this.jwtEncoder = jwtEncoder;
		this.jwtDecoder = jwtDecoder;
	}

	public String generateToken(String username, List<String> ruoloTmp) {
		Instant now = Instant.now();

		JwtClaimsSet claim = JwtClaimsSet.builder()
				.subject(username)
				.issuedAt(now)
				.expiresAt(now.plusSeconds(3600))
				.claim("role", ruoloTmp)
				.build();

		return jwtEncoder.encode(JwtEncoderParameters.from(claim)).getTokenValue();

	}
	
	public String generateRefreshToken(String username) {
		
		
	    Instant now = Instant.now();
	    JwtClaimsSet claims = JwtClaimsSet.builder()
	            .subject(username)
	            .issuedAt(now)
	            .expiresAt(now.plus(7, ChronoUnit.DAYS)) // 7 giorni
	            .claim("type", "refresh") // opzionale ma utile
	            .build();
	    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	public String extractUsername(String token) {
		Jwt jwt = jwtDecoder.decode(token);
		return jwt.getSubject();
	}
	

	public boolean validateToken(String token) {
		try {
			jwtDecoder.decode(token); // se non lancia eccezione → valido
			System.out.println("TOKEN VALIDO DAL RICHIESTA");

			return true;
		} catch (JwtException e) {
			e.printStackTrace();
			return false;
		}
	}
		

		public Authentication getAuthentication(String token) {
			 String username = extractUsername(token); 
			 User user=userService.findByEmail(username);
		    
		    // <-- il tuo metodo che legge il "sub" o username dal token
		    List<String> roles = new ArrayList<String>(); // <-- se i ruoli li hai dentro il token, tipo "role": ["USER"]

		    roles.add(user.getRoles().toString());
		    return new UsernamePasswordAuthenticationToken(
		            username,
		            null,
		            roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
		    );
		
}
		

		
		
		
}
