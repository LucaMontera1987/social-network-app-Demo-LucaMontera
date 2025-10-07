package com.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dto.postDto.PostRequestUpdate;
import com.dto.postDto.PostResponseDto;
import com.dto.postDto.PostResponseUpdate;
import com.enums.StatoVisualizzazione;
import com.enums.tipoNotifica;
import com.model.Foto;
import com.model.Notifica;
import com.model.Post;
import com.model.User;
import com.repository.FotoRepo;
import com.repository.PostRepo;
import com.repository.ProfiloRepo;
import com.repository.UserRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {

	private final PostRepo postRepo;
	private final UserRepo userRepo;
	private final NotificaService notificaService;
	private final FotoService fotoService;
	private final ProfiloService profiloService;
	private final ProfiloRepo profiloRepo;
	private final FotoRepo fotoRepo;
	private final LikesService likesService; 
	private final AmiciziaService amiciziaService;
	


	
	


	public PostService(PostRepo postRepo, UserRepo userRepo, NotificaService notificaService, FotoService fotoService,
			ProfiloService profiloService, ProfiloRepo profiloRepo, FotoRepo fotoRepo, LikesService likesService,
			AmiciziaService amiciziaService) {
		this.postRepo = postRepo;
		this.userRepo = userRepo;
		this.notificaService = notificaService;
		this.fotoService = fotoService;
		this.profiloService = profiloService;
		this.profiloRepo = profiloRepo;
		this.fotoRepo = fotoRepo;
		this.likesService = likesService;
		this.amiciziaService = amiciziaService;
	}

	public PostResponseDto savePost(List<MultipartFile> file, String testo, User user) throws IOException {
	LocalDateTime data=LocalDateTime.now();

		Post postTmp = new Post();
		postTmp.setTesto(testo);
		postTmp.setProfilo(user.getProfiloUser());
		postTmp.setData(data);
		postRepo.save(postTmp);

		if(file!=null) {
		List<Foto> fotoListTmp = fotoService.uploadFotoPost(file, postTmp);

		for(Foto f:fotoListTmp) {
			Foto fotoTmp=new Foto();
			fotoTmp.setPostFoto(postTmp);
			fotoTmp.setProfilo(user.getProfiloUser());
			fotoTmp.setUrl(f.getUrl());
			fotoRepo.save(fotoTmp);
		}
		
		}
		
		List<User>listaAmici=amiciziaService.loadAmiciziaFilter(user);
		
		for(User userTmp:listaAmici) {
		Notifica notificaTmp=new Notifica();
		notificaTmp.setId_autore(user);
		notificaTmp.setContenuto("Nuovo Post da :");
		notificaTmp.setId_evento(postTmp.getId());
		notificaTmp.setDate(data);
		notificaTmp.setTipoNotifica(tipoNotifica.POST);
		notificaTmp.setVisualizzazione(StatoVisualizzazione.NON_LETTO);
		notificaTmp.setId_destinatario(userTmp.getId());
		notificaTmp.setUsernameDestinatario(userTmp.getUsername());
	   notificaService.sendNotifica(notificaTmp,null);
	}
	
	
		List<String> fotoSecure_url = fotoService.getFotoByIdPost(postTmp.getId());

		PostResponseDto responseDto = new PostResponseDto(postTmp.getId() ,postTmp.getProfilo().getUser().getUsername(),
				postTmp.getTesto(), fotoSecure_url,postTmp.getCount(),false);

		return responseDto;

	}

	public void deletePost(Long idPost) {
		Post post = postRepo.findById(idPost).orElseThrow();

		postRepo.delete(post);
	}

	public PostResponseUpdate updatePost(Long idPost, PostRequestUpdate postRequestUpdate,
			Authentication authentication) {
		Post postTmp = postRepo.findById(idPost).orElseThrow(() -> new EntityNotFoundException());
		postTmp.setTesto(postRequestUpdate.testo());
		postRepo.save(postTmp);
		PostResponseUpdate postResponseUpdate = new PostResponseUpdate(null,
				null, null, null, null, false, null,null,null);

		return postResponseUpdate;
	}
	
	
	public Post postByIdResponsePost(Long idPost) {
		Post postTmp = postRepo.findById(idPost).orElseThrow(() -> new EntityNotFoundException());
		return postTmp;
	}

	public PostResponseDto postFindByID(Long IdPost) {
		Post postTmp = postRepo.findById(IdPost).orElseThrow();

		List<String> urlFoto = fotoService.getFotoByIdPost(IdPost);
		// List<String[]> byteFoto= new ArrayList<>();
		PostResponseDto postDto = new PostResponseDto(postTmp.getId(),postTmp.getProfilo().getUser().getUsername(), postTmp.getTesto(),
				urlFoto,postTmp.getCount(),false);

//		PostResponseDto prd=new PostResponseDto(postTmp.getProfilo().get, urlFoto );
		return postDto;

	}

	public PostResponseUpdate getPostByIdPost(Long IdPost) {
         Post postTmp=postRepo.findById(IdPost).orElseThrow()	;

		Post post = postRepo.recevedPostAndUrlPhoto(IdPost).orElseThrow();
		List<String> fotoSecure_url = new ArrayList<>();

		for (Foto f : postTmp.getFoto()) {
			fotoSecure_url.add(f.getUrl());
		}

		PostResponseUpdate postDto = new PostResponseUpdate(postTmp.getId() ,postTmp.getProfilo().getUser().getUsername(), postTmp.getTesto(),
				fotoSecure_url,postTmp.getCount(),false,postTmp.getProfilo().getFotoProfilo().getUrl(),postTmp.getProfilo().getId(),postTmp.getData());

		return postDto;

	}

	public List<PostResponseDto> getAllPostByProfile(User user,int page,int size) {
		
		Pageable pageable=PageRequest.of(page, size);

		List<Post> listPost = postRepo.recevedAllPostByProfilo(user.getProfiloUser().getId());

		List <PostResponseDto> postDto=new ArrayList<>();
		
		for (Post p : listPost) {
			List<String> secure_url = new ArrayList<>();
			boolean likesTmpControl=likesService.controllLike(p.getId(), user);
			System.out.println("DENTRO getAllPostByProfile " +  likesTmpControl);

			for (Foto f : p.getFoto()) {
				secure_url.add(f.getUrl());
				
			}
			PostResponseDto postDtoSingle = new PostResponseDto(p.getId(),p.getProfilo().getUser().getUsername(), p.getTesto(),secure_url,p.getCount(),likesTmpControl);
			postDto.add(postDtoSingle);
			System.out.println("postDtoSingle " + postDtoSingle);
		}
                 return postDto;
		
		
	}
	
	public List<PostResponseDto> getPostProfileFriend(Long idProfile) {
		
       List<Post> postTmp=postRepo.recevedAllPostByProfilo(idProfile);
		List <PostResponseDto> postDto=new ArrayList<>();

       
       for(Post p:postTmp) {
    	   List<String>urlTmp=new ArrayList<String>();
    	   for(Foto f:p.getFoto()) {
    		   urlTmp.add(f.getUrl());
    	   }
    		PostResponseDto postDtoSingle = new PostResponseDto(p.getId(),p.getProfilo().getUser().getUsername(), p.getTesto(),urlTmp,p.getCount(),false);
			postDto.add(postDtoSingle);
    	   
       }
				
		return postDto;
		}
	
	public Page<PostResponseUpdate> getAllPostFriendsList(User user, int page, int size){
		
		Pageable pageable=PageRequest.of(page, size);
		
		List<User> listaAmicizia=amiciziaService.loadAmiciziaFilter(user);
		listaAmicizia.add(user);
		List<Long> idProfili=new ArrayList<>();
		
		List<PostResponseUpdate> postDto=new ArrayList<>();
				
		for(User u:listaAmicizia) {
				
			idProfili.add(u.getProfiloUser().getId());
			System.out.println(u.getProfiloUser().getId() + "PROFILI ID");
			
		}
		
		Page<Post> postAll=postRepo.findAllByUserIdInOrderByCreatedAtDesc(idProfili,pageable);
				
		for(Post p:postAll) {
			
			PostResponseUpdate dto=new PostResponseUpdate(p.getId(), p.getProfilo().getUser().getUsername(),
					p.getTesto(), fotoService.getFotoByIdPost(p.getId()), p.getCount(), likesService.controllLike(p.getId(), user)
					,p.getProfilo().getFotoProfilo().getUrl(),p.getProfilo().getId(),p.getData());
			
			postDto.add(dto);
			System.out.println(dto + "PROFILI ID");
		}
		
		return new PageImpl<>(postDto, pageable, postAll.getTotalElements() );
	}
	
	
	
	

}
