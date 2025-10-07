package com.restController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.commentoDto.CommentoRequestDto;
import com.dto.commentoDto.CommentoResponseDto;
import com.model.User;
import com.service.CommentoService;
import com.service.UserService;

@RestController
@RequestMapping("/commento")
public class CommentoRest {
	
	private final CommentoService commentoService;
	private final UserService userService;
		
@Autowired
	public CommentoRest(CommentoService commentoService, UserService userService) {
		this.commentoService = commentoService;
		this.userService = userService;
	}






	@PostMapping("/save")
	public ResponseEntity<CommentoResponseDto> save(@RequestBody CommentoRequestDto commentoRequestDto, Authentication authentication){
		System.out.println("commento " + commentoRequestDto.testo()  + commentoRequestDto.idPost());
		
		Optional<User> userTmp= userService.findUser(authentication.getName());
		
		CommentoResponseDto dto= commentoService.saveCommento(commentoRequestDto.idPost(), commentoRequestDto.testo(), userTmp.get());
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		
	}
	
	@GetMapping("/getCommenti")
	public ResponseEntity<Page<CommentoResponseDto>> getAllCommenti(@RequestParam("idPost") Long idPost,@RequestParam("page") int page,@RequestParam("size") int size){
		
		Page<CommentoResponseDto> listCommentiDto=commentoService.getAllCommento(idPost, page, size);
		
		return ResponseEntity.status(HttpStatus.OK).body(listCommentiDto);
	}

}
