package com.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.userDto.UserRegisterDto;
import com.enums.Roles;
import com.model.Profilo;
import com.model.User;
import com.service.ProfiloService;
import com.service.UserService;

@RestController
@RequestMapping("/register")
public class registerRest {
	
	private final UserService userService;
	private final ProfiloService profiloService;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	

	



@Autowired
	public registerRest(UserService userService, ProfiloService profiloService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.profiloService = profiloService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}







	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping("/registerUser")
	public ResponseEntity<Void> register(@RequestBody UserRegisterDto userRegisterDto){
		System.out.println(" DENTRO REGISTER " + userRegisterDto);
		User user=new User();
		
		user.setCognome(userRegisterDto.cognome());
		user.setEmail(userRegisterDto.email());
		user.setNome(userRegisterDto.nome());
		user.setUsername(userRegisterDto.username());
		user.setDataNascita(userRegisterDto.data_di_nascita());
		user.setRoles(Roles.USER);
		Profilo profilo=new Profilo();
		profiloService.salva(profilo);
		user.setProfiloUser(profilo);
		
		
		String password=bCryptPasswordEncoder.encode(userRegisterDto.password());
		
		user.setPassword(password);
		
		
		userService.saveUser(user);
		
		
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}

}
