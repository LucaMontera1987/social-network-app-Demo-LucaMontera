package com.restController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.postDto.PostResponseDto;
import com.dto.postDto.PostResponseUpdate;
import com.model.User;
import com.service.NotificaService;
import com.service.PostService;
import com.service.UserService;

@RestController
@RequestMapping("/post")
public class PostRestController {

	private final PostService postService;
	private final UserService userService;
	private final NotificaService notificaService;



	public PostRestController(PostService postService, UserService userService, NotificaService notificaService) {
	
		this.postService = postService;
		this.userService = userService;
		this.notificaService = notificaService;
	}

	@PostMapping("/save")
	public ResponseEntity<PostResponseDto> save(@RequestParam(value= "file",required=false) List<MultipartFile> file, 
			                                    @RequestParam(value="testo",required=false) String testo,
			                                    Authentication authentication) throws IOException {
		
		Optional<User> userTmp=userService.findUser(authentication.getName());
		
		if(userTmp.isPresent()) {
			if(file!=null&&testo.equals("") || file==null&&!testo.equals("") || file!=null&&!testo.equals("")) {
		PostResponseDto postTmp=postService.savePost(file, testo, userTmp.get());
		
	      	
		PostResponseDto postDto=new PostResponseDto(postTmp.idPost() ,userTmp.get().getUsername(), postTmp.testo(), postTmp.url(),postTmp.count(),false);
			
		return ResponseEntity.status(HttpStatus.OK).body(postDto);
		
			}else {
//				PostResponseDto postTmp=postService.savePost(null, testo, userTmp.get());
//				
//		      	
//				PostResponseDto postDto=new PostResponseDto(postTmp.idPost() ,userTmp.get().getUsername(), postTmp.testo(),null,postTmp.count(),false);
//					
//				return ResponseEntity.status(HttpStatus.OK).body(postDto);
			}
		
		}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		
	}
	
	@DeleteMapping("/delete/{idPost}")
	public void delete(@PathVariable Long idPost) {
		postService.deletePost(idPost);
	}
	
	
	
	@GetMapping("/find")
	public ResponseEntity<PostResponseDto> findPost(@RequestParam("idPost") Long idPost){
		
		PostResponseDto postTmp=postService.postFindByID(idPost);
		
		return ResponseEntity.status(HttpStatus.OK).body(postTmp);
		
		
	}

@GetMapping("/allPost")
public ResponseEntity<List<PostResponseDto>> getallPost (Authentication authentication){
	Optional<User> userTmp=userService.findUser(authentication.getName());
	
	if(userTmp.isPresent()) {
	List<PostResponseDto> postTmp=postService.getAllPostByProfile(userTmp.get(), 0, 0);
	
	return ResponseEntity.status(HttpStatus.OK).body(postTmp);
	}else {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	}
}

  @GetMapping("/allPostFriends")
  public ResponseEntity<List<PostResponseDto>> getAllPostFriends(@RequestParam("idProfilo") Long idProfilo){
	  
	 List<PostResponseDto> getAellPostFriends=postService.getPostProfileFriend(idProfilo);
	  
	 return ResponseEntity.status(HttpStatus.OK).body(getAellPostFriends);
  }

  
  @GetMapping("/getAllPost")
  public ResponseEntity<Page<PostResponseUpdate>> getAllPost(Authentication authentication, @RequestParam("page") int page,@RequestParam("size")  int size){
	  System.out.println("GET ALL POST");
		Optional<User> userTmp=userService.findUser(authentication.getName());

			if(userTmp.isPresent())	  {
	 Page<PostResponseUpdate> getAellPostFriends=postService.getAllPostFriendsList(userTmp.get(),page,size);
	 
	

	  
	 return ResponseEntity.status(HttpStatus.OK).body(getAellPostFriends);
			}else {
				 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

			}
	 
	 
  }
  
  @GetMapping("/getSinglePost")
  public ResponseEntity<PostResponseUpdate> getPost(@RequestParam(value="idPost",required=true) Long idPost, Authentication authentication){
	  System.out.println("GET ALL POST");
	  
		Optional<User> userTmp=userService.findUser(authentication.getName());

			if(userTmp.isPresent())	  {
				PostResponseUpdate getAellPostFriends=postService.getPostByIdPost(idPost);
	 	  
	 return ResponseEntity.status(HttpStatus.OK).body(getAellPostFriends);
			}else {
				 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

			}
	 
	 
  }
  
  
  
}
