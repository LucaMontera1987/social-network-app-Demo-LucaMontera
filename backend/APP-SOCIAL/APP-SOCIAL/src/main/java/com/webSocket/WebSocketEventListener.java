package com.webSocket;

import java.security.Principal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.dto.userOnLineWebSocketDto.UserOnlineWebSocket;
import com.model.Amicizia;
import com.model.User;
import com.service.AmiciziaService;
import com.service.UserService;

@Service
public class WebSocketEventListener {

	private final WebSocketuserOnline webSocketuserOnline;
	private final UserService userService;
	private final AmiciziaService amiciziaService;
	private final SimpMessagingTemplate template;

	@Autowired
	public WebSocketEventListener(WebSocketuserOnline webSocketuserOnline, UserService userService,
			AmiciziaService amiciziaService, SimpMessagingTemplate template) {
		this.webSocketuserOnline = webSocketuserOnline;
		this.userService = userService;
		this.amiciziaService = amiciziaService;
		this.template = template;
	}

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		System.out.println("1 DENTRO EVENTLISTNER");

		Set<UserOnlineWebSocket> userOnlineDto = new LinkedHashSet<UserOnlineWebSocket>();
		Set<String> userOnlineMyFriends = new LinkedHashSet<String>();

		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		Principal user = accessor.getUser();
		System.out.println("2 DENTRO EVENTLISTNER" + user.getName() );


		User userTmpByPrincipal = null;

		if (user != null) {

			webSocketuserOnline.insertUserOnline(user.getName());
			userTmpByPrincipal = userService.findByEmail(user.getName());

			for (String s : webSocketuserOnline.getListUserOnline()) {
				for (User u : amiciziaService.loadAmiciziaFilter(userTmpByPrincipal)) {
					if (u.getUsername().equals(s)) {
						userOnlineMyFriends.add(u.getUsername());

					}

				}

				System.out.println("LISTA AMICI ONLINE " + userOnlineMyFriends);

			}

			// invia a me stesso
			for (String s : userOnlineMyFriends) {
				System.out.println("INVIA 1");
				if (!s.equals(user.getName())) {
					Optional<User> userTmp = userService.findUser(s);

					UserOnlineWebSocket userOnlineWebSocket = new UserOnlineWebSocket(userTmp.get().getUsername(),
							userTmp.get().getProfiloUser().getFotoProfilo().getUrl(), userTmp.get().getId());
					userOnlineDto.add(userOnlineWebSocket);
				}

			}
			CompletableFuture.delayedExecutor(1000, TimeUnit.MILLISECONDS)
					.execute(() -> template.convertAndSendToUser(user.getName(), "/queue/userOnLine", userOnlineDto));

			for (String s : userOnlineMyFriends) {
				Set<User> listFriendByMyFriend = new LinkedHashSet<>();
				Set<UserOnlineWebSocket> userOnlineMyFriend = new LinkedHashSet<UserOnlineWebSocket>();

				Optional<User> userTmp = userService.findUser(s);

				for (String a : webSocketuserOnline.getListUserOnline()) {
					for (User u : amiciziaService.loadAmiciziaFilter(userTmp.get())) {
						if (u.getUsername().equals(a)) {
							listFriendByMyFriend.add(u);

							UserOnlineWebSocket userOnlineWebSocket = new UserOnlineWebSocket(u.getUsername(),
									u.getProfiloUser().getFotoProfilo().getUrl(), u.getId());
							userOnlineMyFriend.add(userOnlineWebSocket);

						}

					}

					CompletableFuture.delayedExecutor(3000, TimeUnit.MILLISECONDS)
							.execute(() -> template.convertAndSendToUser(s, "/queue/userOnLine", userOnlineMyFriend));
				}

			}

		}

		

	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		Set<UserOnlineWebSocket> userOnlineDto = new LinkedHashSet<UserOnlineWebSocket>();

		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

		System.out.println("DENTRO EVENTLISTNER Disconnect" + accessor.getUser());

		Principal user = accessor.getUser();

		if (user != null) {
			User userTmp = userService.findByEmail(user.getName());

			webSocketuserOnline.removeUserOnline(userTmp.getUsername());
		}

		for (String s : webSocketuserOnline.getListUserOnline()) {
			if (!s.equals(user.getName())) {
				Optional<User> userTmp = userService.findUser(s);
				UserOnlineWebSocket userOnlineWebSocket = new UserOnlineWebSocket(userTmp.get().getUsername(),
						userTmp.get().getProfiloUser().getFotoProfilo().getUrl(), userTmp.get().getId());
				userOnlineDto.remove(userOnlineWebSocket);
			}
			CompletableFuture.delayedExecutor(1000, TimeUnit.MILLISECONDS).execute(() ->

			template.convertAndSendToUser(user.getName(), "/queue/userOnLine", userOnlineDto));
		}
	}
}
