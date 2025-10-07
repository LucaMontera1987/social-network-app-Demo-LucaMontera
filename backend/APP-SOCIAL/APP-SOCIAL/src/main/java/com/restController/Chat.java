package com.restController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.dto.messagePrivate.MessagePrivateDto;
import com.enums.StatoVisualizzazione;
import com.enums.tipoNotifica;
import com.model.ChatPrivate;
import com.model.Notifica;
import com.model.User;
import com.repository.ChatPrivateRepo;
import com.service.NotificaService;
import com.service.UserService;
import com.webSocket.WebSocketuserOnline;

@Controller
public class Chat {

	private final SimpMessagingTemplate template;
	private final UserService userService;
	private final ChatPrivateRepo chatPrivateRepo;
	private final WebSocketuserOnline webSocketuserOnline;
	private final NotificaService notificaService;

	@Autowired
	public Chat(SimpMessagingTemplate template, UserService userService, ChatPrivateRepo chatPrivateRepo,
			WebSocketuserOnline webSocketuserOnline, NotificaService notificaService) {
		this.template = template;
		this.userService = userService;
		this.chatPrivateRepo = chatPrivateRepo;
		this.webSocketuserOnline = webSocketuserOnline;
		this.notificaService = notificaService;
	}

	@MessageMapping("/chat.private")
	public void messagePrivate(@Payload MessagePrivateDto message, Principal principal) {
		Set<String> userOnline = webSocketuserOnline.getListUserOnline();
		LocalDateTime data = LocalDateTime.now();
		
		System.out.println("1 DENTRO CHAT PRIVATE" + message + principal.getName());
							

		Optional<User> userTmp = userService.findUser(principal.getName());
		Optional<User> userDestinatario = userService.findUser(message.usernameDestinatario());
		String usernameDestinatario=null;

		for (String user : userOnline) {
			
			System.out.println("PLUS FOR DENTRO CHAT PRIVATE" + user);

			
				if(user.equals(userDestinatario.get().getUsername())&&!user.equals(userTmp.get().getUsername())) {
					usernameDestinatario=user;
					   System.out.println("2 DENTRO CHAT PRIVATE" + message + usernameDestinatario);
					   		   				   
				}
				
		}
				if(usernameDestinatario!=null) {
					System.out.println("3 DENTRO CHAT PRIVATE" + message + usernameDestinatario);
				ChatPrivate chat = new ChatPrivate();
				chat.setContenuto(message.message());
				chat.setUtenteInvia(userTmp.get());
				chat.setUtenteRiceve(userDestinatario.get());
				chat.setDateChat(data);
				chatPrivateRepo.save(chat);

				MessagePrivateDto dto = new MessagePrivateDto(chat.getContenuto(), chat.getUtenteInvia().getId(),
						chat.getUtenteRiceve().getUsername(), data);

				template.convertAndSendToUser(userTmp.get().getUsername(), "/queue/chatPrivate", dto);
				template.convertAndSendToUser(chat.getUtenteRiceve().getUsername(), "/queue/chatPrivate", dto);
           	                			
				}else  {
					System.out.println("4 DENTRO CHAT PRIVATE" + message + usernameDestinatario);

					
					ChatPrivate chat = new ChatPrivate();
					chat.setContenuto(message.message());
					chat.setUtenteInvia(userTmp.get());
					chat.setUtenteRiceve(userDestinatario.get());
					chat.setDateChat(data);
					chatPrivateRepo.save(chat);
					
					MessagePrivateDto dto = new MessagePrivateDto(chat.getContenuto(), chat.getUtenteInvia().getId(),
							chat.getUtenteRiceve().getUsername(), data);

					
					template.convertAndSendToUser(userTmp.get().getUsername(), "/queue/chatPrivate", dto);
					
					Notifica notificaTmp = new Notifica();
					notificaTmp.setId_autore(userTmp.get());
					notificaTmp.setVisualizzazione(StatoVisualizzazione.NON_LETTO);
					notificaTmp.setTipoNotifica(tipoNotifica.MESSAGGIO_PRIVATO);
					notificaTmp.setContenuto("Hai un messaggio da parte di:");
					notificaTmp.setDate(data);
					notificaTmp.setId_autore(userTmp.get());
					notificaTmp.setId_destinatario(userDestinatario.get().getId());
					notificaTmp.setUsernameDestinatario(userDestinatario.get().getUsername());
					notificaTmp.setId_evento(chat.getId());
					
					notificaService.sendNotifica(notificaTmp,null);
					

				}
				


		
	}

}
