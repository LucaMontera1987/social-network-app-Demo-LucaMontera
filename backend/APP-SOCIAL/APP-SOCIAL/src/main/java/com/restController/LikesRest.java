package com.restController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.likesDto.LikeResponseDto;
import com.model.Post;
import com.model.User;
import com.repository.Likesrepo;
import com.service.LikeFriendResponseDto;
import com.service.LikesService;
import com.service.PostService;
import com.service.UserService;

@RestController
@RequestMapping("/likes")
public class LikesRest {
	
	private final PostService postService;
	private final LikesService likesService;
	private final UserService userService;
	private final Likesrepo likesrepo;
	
	


     @Autowired
	public LikesRest(PostService postService, LikesService likesService, UserService userService, Likesrepo likesrepo) {
		this.postService = postService;
		this.likesService = likesService;
		this.userService = userService;
		this.likesrepo = likesrepo;
	}

	@PostMapping("/addLike")
	public ResponseEntity<LikeResponseDto> addLike(@RequestParam("idPost") Long idPost,Authentication authentication){
		Optional<User> userTmp=userService.findUser(authentication.getName());
		
		if(userTmp.isPresent()) {
			System.out.println("DENTRO ADD LIKE SERVICE");
		LikeResponseDto LikeResponseDto=likesService.addLike(idPost, userTmp.get());
		
		return ResponseEntity.status(HttpStatus.OK).body(LikeResponseDto);
		}else{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		}

	}
	
	@GetMapping("/count")
	public ResponseEntity<Long> count(@RequestParam("idPost") Long idPost){
		
		
		Post postTmp=postService.postByIdResponsePost(idPost);
				
		Long count=postTmp.getCount();
	
		System.out.println("DENTRO " + count);
		return ResponseEntity.status(HttpStatus.OK).body(count);
	}

	@DeleteMapping("/removeLike")
	public ResponseEntity<LikeResponseDto> removeLike(@RequestParam("idPost") Long idPost, Authentication authentication) {
		
		User userTmp=userService.findByEmail(authentication.getName());
		
		LikeResponseDto likeResponseDto=likesService.removelike(idPost, userTmp);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(likeResponseDto);
		
	}
	
	@GetMapping("/getListLikes")
	public ResponseEntity<List<LikeFriendResponseDto>> getListLikes (@RequestParam("idPost")Long idPost){
	System.out.println("DENTRO getListLikes ");

		
		List <LikeFriendResponseDto> getListLike=likesService.getListLike(idPost);
		
		return ResponseEntity.status(HttpStatus.OK).body(getListLike);
}
	
	}
