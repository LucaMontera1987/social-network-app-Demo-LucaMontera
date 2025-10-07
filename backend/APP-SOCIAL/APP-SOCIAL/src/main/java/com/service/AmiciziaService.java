package com.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.amiciziaDto.AmicoResponseDto;
import com.enums.StatoAmicizia;
import com.enums.StatoVisualizzazione;
import com.enums.tipoNotifica;
import com.model.Amicizia;
import com.model.Notifica;
import com.model.User;
import com.repository.AmiciziaRepo;
import com.repository.NotificaRepo;
import com.repository.UserRepo;

@Service
public class AmiciziaService {
	
	private final LocalDateTime date=LocalDateTime.now();
	private final AmiciziaRepo amiciziaRepo;
	private final UserRepo userRepo;
	private final NotificaRepo notificaRepo;
	private final NotificaService notificaService;

	

	
	@Autowired
	public AmiciziaService(AmiciziaRepo amiciziaRepo, UserRepo userRepo, NotificaRepo notificaRepo,
			NotificaService notificaService) {
		this.amiciziaRepo = amiciziaRepo;
		this.userRepo = userRepo;
		this.notificaRepo = notificaRepo;
		this.notificaService = notificaService;
	}



	public void accetta( Long idAmicizia) {
				
		Amicizia amiciziaTmp=amiciziaRepo.findById(idAmicizia).orElseThrow();
	
		amiciziaTmp.setStatoAmicizia(StatoAmicizia.ACCETTA);
						
	    amiciziaRepo.save(amiciziaTmp); 
		

		
	}
	
	
	
	public AmicoResponseDto invia(User user, Long idRiceve) {
					
		User userInvia=userRepo.findUserByEmailOrUsername(user.getUsername()).orElseThrow();
		
		User userRiceve=userRepo.findByprofiloUser_id(idRiceve).orElseThrow();
		
		System.out.println("AmicoResponseDto INVIA " + null);

		
	Optional <Amicizia> amiciziaIsPresent=amiciziaRepo.controllIfAmiciziaIsTrue(userInvia.getId(), userRiceve.getId());
	
	if(amiciziaIsPresent.isPresent()) {
		System.out.println("AmicoResponseDto " + null);
		return null;
		
	}else {
		
		Amicizia amiciziaTmp=new Amicizia();
		amiciziaTmp.setAmiciziaInviata(userInvia);
		amiciziaTmp.setAmiciziaRicevuta(userRiceve);
		amiciziaTmp.setStatoAmicizia(StatoAmicizia.SOSPESA);
		amiciziaRepo.save(amiciziaTmp);
		
	
		
		Notifica notificaTmp=new Notifica();
		notificaTmp.setId_autore(userInvia);
		notificaTmp.setUsernameDestinatario(userRiceve.getUsername());
		notificaTmp.setId_destinatario(userRiceve.getId());
		notificaTmp.setId_evento(amiciziaTmp.getId());
		notificaTmp.setVisualizzazione(StatoVisualizzazione.NON_LETTO);
		notificaTmp.setTipoNotifica(tipoNotifica.AMICIZIA);
		notificaTmp.setContenuto("Amicizia inviata da ");
		notificaTmp.setDate(date);
		
		notificaService.sendNotifica(notificaTmp,null);
		
		AmicoResponseDto dto=new AmicoResponseDto(userRiceve.getId(), userRiceve.getProfiloUser().getId(), StatoAmicizia.SOSPESA);
	     return dto;
	}
		}
	
	
	
	
	public void rifiuta(User user, Long idRiceve) {
		
		User userInvia=userRepo.findUserByEmailOrUsername(user.getUsername()).orElseThrow();

		User userRiceve=userRepo.findByprofiloUser_id(idRiceve).orElseThrow();

		 Amicizia amiciziaIsPresent=amiciziaRepo.controllIfAmiciziaIsTrue(userInvia.getId(), userRiceve.getId()).orElseThrow();
		 amiciziaRepo.delete(amiciziaIsPresent);
	
		
			
	}

	
	
	
	public void delete(Amicizia amicizia) {
		amiciziaRepo.delete(amicizia);
		}
	
	
	public List<Amicizia> loadAmiciziaAccettata(Long idA){
		System.out.println("loadAmiciziaAccettata " + idA);

		List<Amicizia> amicizie=amiciziaRepo.listAmiciziaAccettata(idA);
		System.out.println("loadAmiciziaAccettata " + amicizie.size());
		return amicizie;
		
	}
	
	public List<User> loadAmiciziaFilter(User userTmp)throws NullPointerException{
		
		List<Amicizia> listaAmicizia=amiciziaRepo.listAmiciziaAccettata(userTmp.getId());
		
		List<User> listaAmici=new ArrayList<>();
		
		for(Amicizia a:listaAmicizia) {
			if(!a.getAmiciziaInviata().getId().equals(userTmp.getId())) {
				listaAmici.add(a.getAmiciziaInviata());
			}else {
				listaAmici.add(a.getAmiciziaRicevuta());
			}
		}
		
		return listaAmici;
	}
	
	public AmicoResponseDto controllIfAmiciziaIsTrue(User user, Long idRiceve) {
		
        User userInvia=userRepo.findUserByEmailOrUsername(user.getUsername()).orElseThrow();
		
		User userRiceve=userRepo.findByprofiloUser_id(idRiceve).orElseThrow();
								
		Optional <Amicizia> amiciziaTmp=amiciziaRepo.controllIfAmiciziaIsTrue(userInvia.getId(), userRiceve.getId());
		
		if(amiciziaTmp.isPresent()) {
			
			AmicoResponseDto dto=new AmicoResponseDto(amiciziaTmp.get().getAmiciziaInviata().getId(), amiciziaTmp.get().getAmiciziaRicevuta().getProfiloUser().getId(), amiciziaTmp.get().getStatoAmicizia());
	       return dto;
		}else {
			
			AmicoResponseDto dto=new AmicoResponseDto(null, null, null);

			return dto;
		}
		   
		   
		   
		
	}
	

}
