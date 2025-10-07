package com.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.security.JwtHandshakeHandler;
import com.security.JwtService;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final JwtService jwtService;
	private final JwtHandshakeHandler jwtHandshakeHandler;

	@Autowired
	public WebSocketConfig(JwtService jwtService, JwtHandshakeHandler jwtHandshakeHandler) {
		this.jwtService = jwtService;
		this.jwtHandshakeHandler = jwtHandshakeHandler;
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws") // il client si collega su loclahost:8080/ws
				.setHandshakeHandler(jwtHandshakeHandler) // 👈 aggancia il tuo handler
				.setAllowedOriginPatterns("http://127.0.0.1:5500", "http://localhost:4200") // serve per i cors del
																							// webSocket
				.withSockJS();

	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic", "/queue"); // canali di destinazione per topic @sentTo(topic/chatroom nel
															// controller
		registry.setApplicationDestinationPrefixes("/app");
	}

}
