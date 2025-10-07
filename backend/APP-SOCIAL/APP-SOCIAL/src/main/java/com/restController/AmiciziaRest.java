package com.restController;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.amiciziaDto.AmicoResponseDto;
import com.dto.amiciziaDto.AmicoSingoloDto;
import com.model.Foto;
import com.model.Post;
import com.model.User;
import com.service.AmiciziaService;
import com.service.ProfiloService;
import com.service.UserService;

@RestController
@RequestMapping("/amici")
public class AmiciziaRest {
	
	private final UserService userService;
	private final  AmiciziaService amiciziaService;
	private final ProfiloService profiloService;
	
 @Autowired
 	public AmiciziaRest(UserService userService, AmiciziaService amiciziaService, ProfiloService profiloService) {
		this.userService = userService;
		this.amiciziaService = amiciziaService;
		this.profiloService = profiloService;
	}

	@PostMapping("/sendAmicizia")
	public ResponseEntity <AmicoResponseDto> inviaAmicizia(Authentication authentication,@RequestParam("idProfiloAmico") Long idProfiloAmico) {
		
		Optional<User> userInvia=userService.findUser(authentication.getName());
				if(userInvia.isPresent()) {
		AmicoResponseDto dto=amiciziaService.invia(userInvia.get(), idProfiloAmico);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
				}else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
				}
	}

	@PostMapping("/AccettaAmicizia")
	public ResponseEntity<Void> accettaAmicizia(Authentication authentication,@RequestParam("idAmicizia") Long idAmicizia) {
		
		Optional<User> userInvia=userService.findUser(authentication.getName());
		if(userInvia.isPresent()) {
				
      	amiciziaService.accetta(idAmicizia);
		
      	return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		}else {
	      	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		}
			
		
	 }
	
	
	@PostMapping("/rifiutaAmicizia")
	public void rifiutaAmicizia(Authentication authentication,@RequestParam("idProfiloAmico") Long idProfiloAmico) {
		System.out.println("DENTRO rifiutaAmicizia " + idProfiloAmico);

		Optional<User> userInvia=userService.findUser(authentication.getName());
				
		amiciziaService.rifiuta(userInvia.get(), idProfiloAmico);
	
	}
	
	
	
	
	@GetMapping("/loadAmiciziaAccettata")
	public ResponseEntity< Set<AmicoSingoloDto> > loadAmiciziaAccettata(Authentication authentication){
		Optional<User> userTmp=userService.findUser(authentication.getName());
		
		if(userTmp.isPresent()) {
	Set<AmicoSingoloDto>  aSd=new LinkedHashSet<AmicoSingoloDto>();
		
		List<User>listaUser=amiciziaService.loadAmiciziaFilter(userTmp.get());
		 for(User u:listaUser) {
			 AmicoSingoloDto amicoSingolo=new AmicoSingoloDto(u.getId(),u.getUsername(),u.getProfiloUser().getFotoProfilo().getUrl(),userTmp.get().getProfiloUser().getId());
			 aSd.add(amicoSingolo);
		 }
		 
		 return ResponseEntity.status(HttpStatus.OK).body(aSd);
		}
		 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	}

	@GetMapping("/loadAmiciProfiloAmico")
	public ResponseEntity< Set<AmicoSingoloDto> > loadAmiciProfiloAmico(@RequestParam("idProfiloAmico") Long idProfiloAmico){
		User userTmp=userService.findByIdProfile(idProfiloAmico);
		System.out.println("loadAmiciProfiloAmico " + userTmp.getUsername());
	Set<AmicoSingoloDto>  aSd=new LinkedHashSet<AmicoSingoloDto>();
		
		List<User>listaUser=amiciziaService.loadAmiciziaFilter(userTmp);
		 for(User u:listaUser) {
			 AmicoSingoloDto amicoSingolo=new AmicoSingoloDto(u.getId(),u.getUsername(),u.getProfiloUser().getFotoProfilo().getUrl(),userTmp.getProfiloUser().getId());
			 aSd.add(amicoSingolo);
		 }
		 
		 return ResponseEntity.status(HttpStatus.OK).body(aSd);
		
	}
	
	@GetMapping("/controllIfAmiciziaIsTrue")
	public ResponseEntity< AmicoResponseDto > controllIfAmiciziaIsTrue(@RequestParam("idProfiloAmico") Long idProfiloAmico, Authentication authentication){
		
		System.out.println("controllIfAmiciziaIsTrue" +idProfiloAmico );

		
		Optional<User> userTmp=userService.findUser(authentication.getName());

		if(userTmp.isPresent()) {
		AmicoResponseDto dto= amiciziaService.controllIfAmiciziaIsTrue(userTmp.get(), idProfiloAmico);
		 
		 return ResponseEntity.status(HttpStatus.OK).body(dto);
		}else {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		}
		
	}
	
	@GetMapping("/loadPhotoFriend")
	public ResponseEntity< List<String> > loadPhotoFriend(Authentication authentication){
		Optional<User> userTmp=userService.findUser(authentication.getName());
		List<String>photo=new ArrayList<String>();
		
		if(userTmp.isPresent()) {
	Set<AmicoSingoloDto>  aSd=new LinkedHashSet<AmicoSingoloDto>();
		
		List<User>listaUser=amiciziaService.loadAmiciziaFilter(userTmp.get());
		
		 for(User u:listaUser) {
			photo.add(u.getProfiloUser().getFotoCopertina().getUrl());
			photo.add(u.getProfiloUser().getFotoProfilo().getUrl());
			for(Post photoTmp:u.getProfiloUser().getUser().getProfiloUser().getPostProfilo()) {
				for(Foto urlPhoto: photoTmp.getFoto()) {
					photo.add(urlPhoto.getUrl());
				}
			}


		 }
		 
		 return ResponseEntity.status(HttpStatus.OK).body(photo);
		}
		 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	}
	
	
}
