package com.webSocket;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.userOnLineWebSocketDto.UserOnlineWebSocket;
import com.model.User;
import com.service.UserService;

@Service
public class WebSocketuserOnline {
	
	@Autowired
	private UserService userService;
		                                                 //multi Thread
		private final Set <String> userOnline=new LinkedHashSet<String>();

		
		public void insertUserOnline(String username ) {
									
			userOnline.add(username);
		}

		
		public void removeUserOnline(String username) {
			userOnline.remove(username);
		}
		
		public Set<String> getListUserOnline() {
			return userOnline;
			
		}
		
	
}
