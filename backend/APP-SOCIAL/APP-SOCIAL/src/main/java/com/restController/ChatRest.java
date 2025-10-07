package com.restController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.messagePrivate.MessagePrivateDto;
import com.model.ChatPrivate;
import com.model.User;
import com.service.ChatService;
import com.service.UserService;

@RestController
@RequestMapping("/chat")
public class ChatRest {
	
	private final ChatService chatService;
	private final UserService userService;
	



@Autowired
	public ChatRest(ChatService chatService, UserService userService) {
		this.chatService = chatService;
		this.userService = userService;
	}




@GetMapping("/listMessage")
	public ResponseEntity<List<MessagePrivateDto>>getListMessage(Authentication authentication,  @RequestParam(value="idDestinatario",required=true)Long idDestinatario){
	LocalDateTime data=LocalDateTime.now();	
	
	    User userTmp=userService.findUser(authentication.getName()).orElseThrow();
		
		List<ChatPrivate> getMessageList=chatService.getListMessage(userTmp, idDestinatario);
		List<MessagePrivateDto>listDto=new ArrayList<>();
		
		
		for(ChatPrivate chat:getMessageList) {
			MessagePrivateDto dto=new MessagePrivateDto(chat.getContenuto(), chat.getUtenteInvia().getId(),chat.getUtenteRiceve().getUsername(), data);
			listDto.add(dto);
		}
		System.out.println("LIST MESSAGE" + listDto.size());
		
		return ResponseEntity.status(HttpStatus.OK).body(listDto);
		
		
	}

}
