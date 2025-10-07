package com.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.profilo.ProfiloResponseDto;
import com.model.Foto;
import com.model.Profilo;
import com.model.User;
import com.repository.FotoRepo;
import com.repository.ProfiloRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfiloService {

	private final ProfiloRepo profiloRepo;
	private final UserService userService;
	private final FotoService fotoService;
	private final FotoRepo fotoRepo;
	

@Autowired
	public ProfiloService(ProfiloRepo profiloRepo, UserService userService, FotoService fotoService,
			FotoRepo fotoRepo) {
		this.profiloRepo = profiloRepo;
		this.userService = userService;
		this.fotoService = fotoService;
		this.fotoRepo = fotoRepo;
	}


                public void salva(Profilo profilo) {
                	profiloRepo.save(profilo);
            }


	public Profilo salvaCopertina(User user,Foto foto, Profilo profilo) {
		
		
		Set<Foto> listaFoto=new LinkedHashSet<Foto>();
		listaFoto.add(foto);
		
	
		profilo.setFotoCopertina(foto);
		profilo.setListaFotoProfilo(listaFoto);
		
		 
		 profiloRepo.save(profilo);
		 
		 
		 
		
		return profilo;
		
	}
	
	
	public Profilo salvaFotoProfilo(User user,Foto foto, Profilo profilo) {
				
		Set<Foto> listaFoto=new LinkedHashSet<Foto>();
		listaFoto.add(foto);
		
	
		profilo.setFotoProfilo(foto);
		profilo.setListaFotoProfilo(listaFoto);
		 
		 profiloRepo.save(profilo);
		 		
		return profilo;
		}
	
		
	
	public byte[] getPhotoProfile(Profilo profilo){
		
		 Profilo profiloTmp=profiloRepo.findById(profilo.getId()).orElseThrow();
		 System.out.println("PROFILOsERVICE PROFILE" + profiloTmp.getFotoProfilo().getUrl());

		byte[] photoprofile= fotoService.sendFotoByteProfilo(profiloTmp.getFotoProfilo().getUrl());
		 return photoprofile;
	}
	
	
	public byte[] getPhotoCopertina(Profilo profilo){
		
		 Profilo profiloTmp=profiloRepo.findById(profilo.getId()).orElseThrow();
		 System.out.println("PROFILOsERVICE COPERTINA" + profiloTmp.getFotoCopertina().getUrl());
		byte[] photoprofile= fotoService.sendFotoByteProfilo(profiloTmp.getFotoCopertina().getUrl());
		 return photoprofile;
	}
	
	public Profilo getProfilo(Long idProfilo) {
		Profilo profiloTmp=profiloRepo.findById(idProfilo).orElseThrow(()->new EntityNotFoundException());
		return profiloTmp;
	}
	
	
}
