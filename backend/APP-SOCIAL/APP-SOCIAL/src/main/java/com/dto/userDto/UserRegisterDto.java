package com.dto.userDto;

import java.time.LocalDate;

public record UserRegisterDto(
	    String username,
	     String  nome,
	     String cognome,
	    String email,
	     String password,
	      String confirm_password,
	     LocalDate  data_di_nascita
		
		) {

}
