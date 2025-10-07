package com.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dto.commentoDto.CommentoResponseDto;
import com.enums.StatoVisualizzazione;
import com.enums.tipoNotifica;
import com.model.Commento;
import com.model.Notifica;
import com.model.Post;
import com.model.User;
import com.repository.CommentoRepo;
import com.repository.PostRepo;
import com.repository.UserRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentoService {

	private final PostRepo postRepo;
	private final NotificaService notificaService;
	private final CommentoRepo commentoRepo;
	private final UserRepo userRepo;

	@Autowired
	public CommentoService(PostRepo postRepo, NotificaService notificaService, CommentoRepo commentoRepo,
			UserRepo userRepo) {
		this.postRepo = postRepo;
		this.notificaService = notificaService;
		this.commentoRepo = commentoRepo;
		this.userRepo = userRepo;
	}

	public CommentoResponseDto saveCommento(Long idPost, String testo, User user) {
		
		LocalDateTime data=LocalDateTime.now();
				
		
		Post post = postRepo.findById(idPost).orElseThrow();
				
		User userTmp = userRepo.findUserByEmailOrUsername(user.getUsername())
				.orElseThrow(() -> new EntityNotFoundException());
		
	
		
		Commento commento = new Commento();
		commento.setCommentiPost(post);
		commento.setCommentoUser(user);
		commento.setContenuto(testo);
		commento.setDate(data);
		commentoRepo.save(commento);

		List <Commento>loadCommenti=commentoRepo.loadCommento(post.getId());
		Set<String> username=new LinkedHashSet<>();
	
		
		for(Commento commentoTmp:loadCommenti) {
		System.out.println("DENTRO COMMENTO SERVICE SAVE " + commentoTmp.getCommentoUser().getUsername());
			
			username.add(commentoTmp.getCommentoUser().getUsername());
			
		}
		
		
	        
	   if(userTmp.getUsername().equals(post.getProfilo().getUser().getUsername())) {
		   username.remove(post.getProfilo().getUser().getUsername());
		   username.remove(userTmp.getUsername());

		   
		   for(String usernameTmp: username) {
		        User destinatario = userRepo.findUserByEmailOrUsername(usernameTmp).orElseThrow();
		        
		        System.out.println("DENTRO SAVE COMMENTO SERVICE " + usernameTmp);
		   
		Notifica notifica = new Notifica();
		notifica.setId_evento(commento.getId());
		notifica.setTipoNotifica(tipoNotifica.COMMENTO);
		notifica.setVisualizzazione(StatoVisualizzazione.NON_LETTO);
		notifica.setId_user_notifica(user);
		notifica.setContenuto("ha commentato il tuo post ");
		notifica.setId_destinatario(destinatario.getId());
		notifica.setUsernameDestinatario(usernameTmp);
		notifica.setPostId(post.getId());
		notifica.setDate(data);
		notificaService.sendNotifica(notifica,post.getId());
		   }
		   
		   
	
	    }else if (!userTmp.getUsername().equals(post.getProfilo().getUser().getUsername())) {
	    	 username.remove(userTmp.getUsername());
	    		username.add(post.getProfilo().getUser().getUsername());
	    	for(String usernameTmp: username) {
	    		
	    		 System.out.println("DENTRO SAVE COMMENTO SERVICE else if " + usernameTmp);
	    		
	    	User userTmp1=userRepo.findUserByEmailOrUsername(usernameTmp).orElseThrow();
	    	Notifica notifica = new Notifica();
			notifica.setId_evento(commento.getId());
			notifica.setTipoNotifica(tipoNotifica.COMMENTO);
			notifica.setVisualizzazione(StatoVisualizzazione.NON_LETTO);
			notifica.setId_user_notifica(user);
			notifica.setContenuto("ha commentato il tuo post ");
			notifica.setId_destinatario(userTmp1.getId());
			notifica.setUsernameDestinatario(usernameTmp);
			notifica.setPostId(post.getId());

			notifica.setDate(data);
			notificaService.sendNotifica(notifica,post.getId());
	    	}
	    	
	    }
	   
	   
	
		
		CommentoResponseDto dto=new CommentoResponseDto(commento.getId(), user.getUsername(), commento.getContenuto(), commento.getCommentoUser().getProfiloUser().getFotoProfilo().getUrl(),commento.getDate());

		return dto;
	}
	
	public Page<CommentoResponseDto> getAllCommento(Long idPost,int page, int size) {
		
		Pageable pageable=PageRequest.of(page, size);
		
		Page <Commento> allCommenti=commentoRepo.loadCommentoPageable(idPost,pageable);
		
		List <CommentoResponseDto> allCommentiDto=new ArrayList<>();

		
		 return allCommenti.map(commento -> new CommentoResponseDto(
			        idPost,
			        commento.getCommentoUser().getUsername(),
			        commento.getContenuto(),
			        commento.getCommentoUser().getProfiloUser().getFotoProfilo().getUrl(),
			        commento.getDate()
			    ));

		
	}

}
