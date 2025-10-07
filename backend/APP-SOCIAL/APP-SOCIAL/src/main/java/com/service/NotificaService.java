package com.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.dto.notificaDto.NotificaResponseDto;
import com.enums.StatoVisualizzazione;
import com.enums.tipoNotifica;
import com.model.Notifica;
import com.model.User;
import com.repository.CommentoRepo;
import com.repository.NotificaRepo;
import com.webSocket.WebSocketuserOnline;

@Service
public class NotificaService {

	private static LocalDateTime date = LocalDateTime.now();
	private final NotificaRepo notificaRepo;
	private final UserService userService;
	private final JmsTemplate jmsTemplate;
	private final SimpMessagingTemplate template;
	private final WebSocketuserOnline webSocketuserOnline;
	private final CommentoRepo commentoRepo;


	

@Autowired
	public NotificaService(NotificaRepo notificaRepo, UserService userService, JmsTemplate jmsTemplate,
			SimpMessagingTemplate template, WebSocketuserOnline webSocketuserOnline, CommentoRepo commentoRepo) {
		this.notificaRepo = notificaRepo;
		this.userService = userService;
		this.jmsTemplate = jmsTemplate;
		this.template = template;
		this.webSocketuserOnline = webSocketuserOnline;
		this.commentoRepo = commentoRepo;
	}

	public Notifica saveNotifica(Notifica notifica) {
		return notificaRepo.save(notifica);

	}

	public void sendNotifica(Notifica notifica, Long idPost) {
		Optional<User> userTmpDestinatario=userService.findUser(notifica.getUsernameDestinatario());
		Optional<User> userTmpMittente=userService.findUser(notifica.getId_autore().getUsername());



		if (notifica.getTipoNotifica().equals(tipoNotifica.AMICIZIA)) {
		

			notificaRepo.save(notifica);
			NotificaResponseDto dto=new NotificaResponseDto(notifica.getId_autore().getUsername(),
					notifica.getContenuto(), notifica.getId_autore().getProfiloUser().getFotoProfilo().getUrl(),
					notifica.getId_evento(),notifica.getTipoNotifica(),
					notifica.getUsernameDestinatario(),userTmpDestinatario.get().getProfiloUser().getId(),null);

			jmsTemplate.setDeliveryPersistent(true);
			jmsTemplate.convertAndSend("coda.notifica", dto);

			
		} else if (notifica.getTipoNotifica().equals(tipoNotifica.COMMENTO)) {
						
			notificaRepo.save(notifica);
			NotificaResponseDto dto=new NotificaResponseDto(notifica.getId_autore().getUsername(),
					notifica.getContenuto(), notifica.getId_autore().getProfiloUser().getFotoProfilo().getUrl(),
					notifica.getId_evento(),notifica.getTipoNotifica(),
					notifica.getUsernameDestinatario(),userTmpDestinatario.get().getProfiloUser().getId(),notifica.getPostId());
			
          jmsTemplate.setDeliveryPersistent(true);
          jmsTemplate.convertAndSend("coda.notifica", dto);
		

		}

		else if (notifica.getTipoNotifica().equals(tipoNotifica.LIKE)) {

						

		} else if (notifica.getTipoNotifica().equals(tipoNotifica.POST)) {
						
			notificaRepo.save(notifica);
			
			NotificaResponseDto dto=new NotificaResponseDto(notifica.getId_autore().getUsername(), 
					notifica.getContenuto(), notifica.getId_autore().getProfiloUser().getFotoProfilo().getUrl(),
					notifica.getId_evento(),notifica.getTipoNotifica(),
					notifica.getUsernameDestinatario(),userTmpDestinatario.get().getProfiloUser().getId(),null);

			jmsTemplate.setDeliveryPersistent(true);
			jmsTemplate.convertAndSend("coda.notifica", dto);
			

		}else if(notifica.getTipoNotifica().equals(tipoNotifica.MESSAGGIO_PRIVATO)) {
			Set<String> userOnline=webSocketuserOnline.getListUserOnline();

			for(String userOnlineTmp:userOnline) {
				User userTmp=userService.findById(notifica.getId_destinatario());
				if(userTmp==null) {
					notificaRepo.save(notifica);
					
					NotificaResponseDto dto=new NotificaResponseDto(notifica.getId_autore().getUsername(),
							notifica.getContenuto(), notifica.getId_autore().getProfiloUser().getFotoProfilo().getUrl(),
							notifica.getId_evento(),notifica.getTipoNotifica(),
							notifica.getUsernameDestinatario(),userTmpDestinatario.get().getProfiloUser().getId(),null);

					jmsTemplate.setDeliveryPersistent(true);
					jmsTemplate.convertAndSend("coda.notifica", dto);
					
				}else {
					notificaRepo.save(notifica);
				}
			}

		
			
		}

	}

	@JmsListener(destination = "coda.notifica")
	public void riceviNotifiche(NotificaResponseDto notifica) {

		System.out.println("1 SONO DENTRO  JMS LISTENER");

		if (notifica.tipo().equals(tipoNotifica.AMICIZIA)) {
			
			
			CompletableFuture.delayedExecutor(1000, TimeUnit.MILLISECONDS)
			.execute(() -> 
			template.convertAndSendToUser(notifica.userDestinatario(), "/queue/notifiche",List.of(notifica) ));
			

		} else if (notifica.tipo().equals(tipoNotifica.COMMENTO)) {
			
			System.out.println("COOMENTO SONO DENTRO  JMS LISTENER " + notifica);
			CompletableFuture.delayedExecutor(500, TimeUnit.MILLISECONDS)
			.execute(() -> 
			template.convertAndSendToUser(notifica.userDestinatario(), "/queue/notifiche", List.of(notifica)));
				
		}

		else if (notifica.tipo().equals(tipoNotifica.LIKE)) {
			
			
			
			template.convertAndSendToUser(notifica.userDestinatario(), "/queue/notifiche", List.of(notifica));
			

		} else if (notifica.tipo().equals(tipoNotifica.POST)) {
			
			System.out.println("2 SONO DENTRO SEND NOTIFICA POST IN JMS LISTENER");
			
			CompletableFuture.delayedExecutor(1000, TimeUnit.MILLISECONDS)
			.execute(() -> 
			template.convertAndSendToUser(notifica.userDestinatario(), "/queue/notifiche", List.of(notifica)));

		
		}else if (notifica.tipo().equals(tipoNotifica.MESSAGGIO_PRIVATO)) {
			
			System.out.println("2 SONO DENTRO SEND NOTIFICA MESSAGGIO PRIVATO IN JMS LISTENER");
			
			CompletableFuture.delayedExecutor(1000, TimeUnit.MILLISECONDS)
			.execute(() -> 
			template.convertAndSendToUser(notifica.userDestinatario(), "/queue/notifiche", List.of(notifica)));


		}


	}

	public List <NotificaResponseDto> getNotifiche(User user) {
		List<Notifica> notificaList=notificaRepo.loadNotifica(user.getId());

		List<NotificaResponseDto> notificaDto=new ArrayList<NotificaResponseDto>();

		
		for(Notifica n:notificaList) {
			User userTmp=userService.findById(n.getId_destinatario());
			
	        NotificaResponseDto dto=new NotificaResponseDto(n.getId_autore().getUsername(),
			n.getContenuto(), 
			n.getId_autore().getProfiloUser().getFotoProfilo().getUrl(),
			n.getId_evento(),
			n.getTipoNotifica(),n.getUsernameDestinatario(),userTmp.getProfiloUser().getId(),n.getPostId());
	
			notificaDto.add(dto);
			
			System.out.println("4 DENTRO NOTIFICA SERVICE GET NOTIFICHE " + notificaDto);

		}
		return notificaDto;
	}
	
	public boolean notificaLetta(Long idEvento, User user)throws Exception {
		System.out.println("3 DENTRO LETTA" + idEvento + user.getId());

		Optional<Notifica> notificaTmp=notificaRepo.notificaLetta(idEvento, user.getId());
		System.out.println("4 DENTRO LETTA");

		boolean remove=false;
		
		if (notificaTmp.isPresent()) {
			System.out.println("5 DENTRO LETTA");

			notificaTmp.get().setVisualizzazione(StatoVisualizzazione.LETTO);
			notificaRepo.save(notificaTmp.get());
			System.out.println("6 DENTRO LETTA");

			remove=true;
					
		return remove;
		}
		
		return remove;
	}
	
	public List<Notifica> notificaNotReadMessage(Long idDestinatario) {
        List<Notifica> notifica=notificaRepo.notificaNotReadMessage(idDestinatario);
        
        return notifica;
	}
	
	
}
