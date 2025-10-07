package com.globalException;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dto.exceptionDto.ExceptionDto;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ExceptionDto> usernameNotFound(UsernameNotFoundException e, HttpServletRequest request){
		
		LocalDateTime time=LocalDateTime.now();
		
		ExceptionDto error=new ExceptionDto
	(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", "login failed", time, request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}
	
	
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ExceptionDto> EntityNotFound(EntityNotFoundException e, HttpServletRequest request){
		LocalDateTime time=LocalDateTime.now();

		
		ExceptionDto error=new ExceptionDto(HttpStatus.NOT_FOUND.value(), "Not Found", "user not found", time, request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionDto> badCredentials(BadCredentialsException e, HttpServletRequest request){
		LocalDateTime time=LocalDateTime.now();

		
		ExceptionDto error=new ExceptionDto(HttpStatus.NOT_FOUND.value(), "user not found", "Credential error", time, request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ExceptionDto> badCredentials(NullPointerException e, HttpServletRequest request){
		LocalDateTime time=LocalDateTime.now();

		
		ExceptionDto error=new ExceptionDto(HttpStatus.NOT_FOUND.value(), "because ", "null", time, request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ExceptionDto> badCredentials(IllegalStateException e, HttpServletRequest request){
		LocalDateTime time=LocalDateTime.now();

		
		ExceptionDto error=new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error remove ", "null", time, request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	

}
