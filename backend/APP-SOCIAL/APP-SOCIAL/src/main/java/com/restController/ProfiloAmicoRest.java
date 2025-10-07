package com.restController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.profiloAmico.ProfiloAmicoDto;
import com.model.User;
import com.repository.UserRepo;
import com.service.UserService;

@RestController
@RequestMapping("/profiloAmico")
public class ProfiloAmicoRest {
	
	private final UserService userService;

	
	
	@Autowired
	public ProfiloAmicoRest(UserService userService) {
		this.userService = userService;
	}



	@GetMapping("/getAmico")
	public ResponseEntity<ProfiloAmicoDto> getProfiloAmico(Authentication authentication,@RequestParam("idProfiloAmico") Long idProfiloAmico){
		Optional<User> userPrincipal=userService.findUser(authentication.getName());
		
		if(userPrincipal.isPresent()) {
		User userTmp=userService.findByIdProfile(idProfiloAmico);
		System.out.println("DENTRO GET AMICO " + userTmp.getCognome());

     if(!userTmp.getUsername().isEmpty()) {
    	User userAmico= userService.findById(userTmp.getId());
		System.out.println("DENTRO GET AMICO " + userAmico.getCognome());
		
    	 ProfiloAmicoDto profiloAmicoDto=new ProfiloAmicoDto(userAmico.getProfiloUser().getId(), userAmico.getUsername(),
    			 userAmico.getProfiloUser().getFotoCopertina().getUrl(),userAmico.getProfiloUser().getFotoProfilo().getUrl(),false,true,userPrincipal.get().getProfiloUser().getId());
    	 return ResponseEntity.status(HttpStatus.OK).body(profiloAmicoDto);
    	 
     }
     
     }else {
    	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
     }
		
		
   	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
	}
	
	
	
	

}
