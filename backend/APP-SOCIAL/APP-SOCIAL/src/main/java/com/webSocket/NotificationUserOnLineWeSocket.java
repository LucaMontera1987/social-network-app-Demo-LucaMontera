package com.webSocket;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.dto.userOnLineWebSocketDto.UserOnlineWebSocket;
import com.model.User;
import com.service.UserService;

@Controller
public class NotificationUserOnLineWeSocket {

	private final WebSocketuserOnline webSocketuserOnline;
	private final SimpMessagingTemplate template;
	private final UserService userService;

	@Autowired
	public NotificationUserOnLineWeSocket(WebSocketuserOnline webSocketuserOnline, SimpMessagingTemplate template,
			UserService userService) {
		this.webSocketuserOnline = webSocketuserOnline;
		this.template = template;
		this.userService = userService;
	}

	@MessageMapping("/chat.userOnLine")
	public void usernLinewebSocket(@Payload UserOnlineWebSocket userOnlineWebSocketTmp,  Principal principal) {

	}

}
