package com.restController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.notificaDto.NotificaResponseDto;
import com.enums.tipoNotifica;
import com.model.Notifica;
import com.model.User;
import com.service.NotificaService;
import com.service.UserService;

@RestController
@RequestMapping("/notifica")
public class NotificaRest {
	
	
	private final NotificaService notificaService;
	private final UserService userService;
	
	@Autowired
	public NotificaRest(NotificaService notificaService, UserService userService) {
		this.notificaService = notificaService;
		this.userService = userService;
	}


	@GetMapping("/getNotificaNotReading")
	public ResponseEntity <List<NotificaResponseDto>> findNotificaNotReading(Authentication authentication ){
		
		Optional<User> userTmp=userService.findUser(authentication.getName());
		
		if(userTmp.isPresent()) {
		List <NotificaResponseDto> dto=notificaService.getNotifiche(userTmp.get());
	
		
		
	  return ResponseEntity.status(HttpStatus.OK).body(dto);
		
		
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}

	}
	
	
	
	@GetMapping("/getNotificaNotReadingMessage")
	public ResponseEntity <List<NotificaResponseDto>> findNotificaMessage(Authentication authentication){
		
		Optional<User> userTmp=userService.findUser(authentication.getName());
		
		if(userTmp.isPresent()) {
			
		List <Notifica> notificaList=notificaService.notificaNotReadMessage(userTmp.get().getId());
		List <NotificaResponseDto> notificaListDto=new ArrayList<NotificaResponseDto>();

		
		for(Notifica notificaTmp:notificaList) {
			
			NotificaResponseDto dto=new NotificaResponseDto(notificaTmp.getId_autore().getUsername(),
					notificaTmp.getContenuto(), notificaTmp.getId_autore().getProfiloUser().getFotoProfilo().getUrl(),
					notificaTmp.getId_evento(), tipoNotifica.MESSAGGIO_PRIVATO, 
					notificaTmp.getUsernameDestinatario(),notificaTmp.getId_autore().getProfiloUser().getId(),null);
			        notificaListDto.add(dto);
			        System.out.println(dto + "getNotificaNotReadingMessage");
		}
	
	
		
	  return ResponseEntity.status(HttpStatus.OK).body(notificaListDto);
		
		
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}

	}
	
	
	@PutMapping("/letta")
	public ResponseEntity<Void> remove(@RequestParam("idEvento") Long idEvento, Authentication authentication) throws Exception{
		Optional<User> userTmp=userService.findUser(authentication.getName());
		
		System.out.println("1 DENTRO LETTA" + idEvento + userTmp.get().getId());
		
		if(userTmp.isPresent()) {
				System.out.println("2 DENTRO LETTA");
		notificaService.notificaLetta(idEvento,userTmp.get() );
		return ResponseEntity.status(HttpStatus.OK).build();
 		
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		
	}

}
