package com.restController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.amiciziaDto.AmicoSingoloDto;
import com.model.User;
import com.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRest {
	
	
	private final UserService userService;
	
@Autowired
	public UserRest(UserService userService) {
		this.userService = userService;
	}





	@GetMapping("/search")
	public ResponseEntity<List<AmicoSingoloDto>> search(@RequestParam("termine") String termine, Authentication authentication){
		Optional<User> userTmp=userService.findUser(authentication.getName());

		if(userTmp.isPresent()) {
		List <User> getSearch=userService.search(termine);
		List <AmicoSingoloDto>getSearchDto=new ArrayList<AmicoSingoloDto>();
		
		for(User u:getSearch) {
			if(u.getId().equals(userTmp.get().getId())) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}else {
			AmicoSingoloDto dto=new AmicoSingoloDto(u.getProfiloUser().getId(), u.getUsername(), u.getProfiloUser().getFotoProfilo().getUrl(), null);
			getSearchDto.add(dto);
			}
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(getSearchDto);
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		}
	}
	
}
