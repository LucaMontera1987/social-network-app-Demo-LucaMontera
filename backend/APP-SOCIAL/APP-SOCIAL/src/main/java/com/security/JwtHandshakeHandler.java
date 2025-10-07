package com.security;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Service
public class JwtHandshakeHandler extends DefaultHandshakeHandler{
	
	private final JwtService jwtService;

	@Autowired
	public JwtHandshakeHandler(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	 @Override
	    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
	        String token = extractTokenFromRequest(request);
	        
	        System.out.println("jwtHandSHAKE " + token);

	        if (token != null && jwtService.validateToken(token)) { // uso il tuo metodo
	            String username = jwtService.extractUsername(token); // uso il tuo metodo
	            System.out.println("jwtHandSHAKE USERNAME" + username);
	            // Creo un Principal direttamente
	            return new Principal() {
	                @Override
	                public String getName() {
	                    return username;
	                }
	            };
	        }

	       throw new IllegalArgumentException ("Invalid or missing JWT token in WebSocket handshake\""); // se il token non è valido o assente
	      
	    }

	 private String extractTokenFromRequest(ServerHttpRequest request) {
		    String query = request.getURI().getQuery();
		    System.out.println("extractTokenFromRequest " + query);
		    if (query != null) {
		        for (String param : query.split("&")) {
		            if (param.startsWith("tokenAccess=")) {
		            	
		    		    System.out.println("extractTokenFromRequest param " + param);

		    		    String token = param.substring("tokenAccess=".length());
                              return token;
		                
		            }
		        }
		    }
		    return null;
		}

}
