package com.restController;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.profilo.GetProfiloDto;
import com.dto.profilo.ProfiloResponseDto;
import com.model.Foto;
import com.model.Profilo;
import com.model.User;
import com.repository.FotoRepo;
import com.repository.ProfiloRepo;
import com.service.FotoService;
import com.service.ProfiloService;
import com.service.UserService;

@RestController
@RequestMapping("/profile")
public class ProfiloRestController {
	
	private final ProfiloRepo profiloRepo;
	private final UserService userService;
	private final FotoService fotoService;
	private final FotoRepo fotoRepo;
	private final ProfiloService profiloService;
	

@Autowired
	public ProfiloRestController(ProfiloRepo profiloRepo, UserService userService, FotoService fotoService,
			FotoRepo fotoRepo,ProfiloService profiloService) {
		this.profiloRepo = profiloRepo;
		this.userService = userService;
		this.fotoService = fotoService;
		this.fotoRepo = fotoRepo;
		this.profiloService=profiloService;
	}
	
	@PostMapping("/salvaImmagineCopertina")
	public ResponseEntity<byte[]> salvaImmagineCopertina(
			Authentication authentication,@RequestParam("file") MultipartFile file) throws IOException{
						
		Optional<User> userTmp=userService.findUser(authentication.getName());
		
		if(userTmp.isPresent()) {
		Profilo profiloTmp=profiloRepo.findById(userTmp.get().getProfiloUser().getId()).orElseThrow();

		Foto fotoTmp=fotoService.uploadFotoProfilo(file, profiloTmp);
		
		profiloService.salvaCopertina(userTmp.get(), fotoTmp, profiloTmp);
						
		return ResponseEntity.status(HttpStatus.OK).body(file.getBytes());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
					

		}
				
	}
	
	@PostMapping("/savePhotoProfilo")
	public ResponseEntity<byte[]> savePhotoProfilo(Authentication authentication, @RequestParam MultipartFile file) throws IOException{
		
		Optional<User> userTmp=userService.findUser(authentication.getName());
		if(userTmp.isPresent()) {
		Profilo profiloTmp=profiloRepo.findById(userTmp.get().getProfiloUser().getId()).orElseThrow();
		
		Foto fotoTmp=fotoService.uploadFotoProfilo(file, profiloTmp);
		profiloService.salvaFotoProfilo(userTmp.get(), fotoTmp, profiloTmp);
	  	
		
		return ResponseEntity.status(HttpStatus.OK).body(file.getBytes());
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()	;

		}
		
	}
	
	@GetMapping("/getProfilo")
	public ResponseEntity<GetProfiloDto> getProfile(Authentication authentication){
	    Optional<User> userTmp=userService.findUser(authentication.getName());

	    if(userTmp.isPresent()) {
		GetProfiloDto gPd=new GetProfiloDto(userTmp.get().getProfiloUser().getId(),userTmp.get().getUsername());
		
		return ResponseEntity.status(HttpStatus.OK).body(gPd);
	    }else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	    }
		
	}
	
	@GetMapping("/loadPhotoProfile")
	public ResponseEntity<byte[]>loadPhotoProfile(Authentication authentication){
	    Optional<User> userTmp=userService.findUser(authentication.getName());
	    
	    if(userTmp.isPresent()) {
	    byte[] photoProfile= profiloService.getPhotoProfile(userTmp.get().getProfiloUser());
	    
	    return ResponseEntity.status(HttpStatus.OK).body(photoProfile);
	    }else {
		    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()	;

	    }

	}
	
	@GetMapping("/loadPhotoCopertina")
	public ResponseEntity<byte[]>loadPhotoCopertina(Authentication authentication){
	    Optional<User> userTmp=userService.findUser(authentication.getName());
	    
	    if(userTmp.isPresent()) {
	    byte[] photoCopertina= profiloService.getPhotoCopertina(userTmp.get().getProfiloUser());
	    
	    return ResponseEntity.status(HttpStatus.OK).body(photoCopertina);
	    }else {
		    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	    }

	}

	
	

}
