package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.likesDto.LikeResponseDto;
import com.model.Likes;
import com.model.Post;
import com.model.User;
import com.repository.Likesrepo;
import com.repository.PostRepo;
import com.repository.UserRepo;

@Service
public class LikesService {

	private final Likesrepo likesRepo;
	private final PostRepo postRepo;
	private final UserRepo userRepo;
	private final NotificaService notificaService;

	@Autowired
	public LikesService(Likesrepo likesRepo, PostRepo postRepo, UserRepo userRepo, NotificaService notificaService) {
		this.likesRepo = likesRepo;
		this.postRepo = postRepo;
		this.userRepo = userRepo;
		this.notificaService = notificaService;
	}

	public Likes save(Likes like) {
		return likesRepo.save(like);
	}

	public void delete(Likes like) {
		likesRepo.delete(like);
	}

	public Long count(Long id) {
		return likesRepo.countLikes(id);
	}

	public boolean controllLike(Long idPost, User user) {

		Optional<Likes> likesTmpControl = likesRepo.controlIfLikeIsPresent(user.getId(), idPost);

		if (likesTmpControl.isPresent()) {
			boolean likesResponseDto = true;

			return likesResponseDto;
		} else {

			boolean likesResponseDto = false;

			return likesResponseDto;
		}

	}

	public LikeResponseDto addLike(Long idPost, User user) {

		if (controllLike(idPost, user) == false) {
			
			Post postTmp = postRepo.findById(idPost).orElseThrow();
			
			if(postTmp.getCount()==null) {
			postTmp.setCount(0L);
			}
			if(postTmp.getCount()>=0) {
			postTmp.setCount(postTmp.getCount() + 1L);
			}
            postRepo.save(postTmp);
			Likes likeTmp = new Likes();
			likeTmp.setaChiPiace(user);
			likeTmp.setLikePost(postTmp);
			likesRepo.save(likeTmp);
			LikeResponseDto likeResponseDto = new LikeResponseDto(postTmp.getCount(), true);
			return likeResponseDto;

		} else {
			return null;
		}

	}

	public LikeResponseDto removelike(Long idPost, User user) {

		Optional<Likes> likesTmp = likesRepo.controlIfLikeIsPresent(user.getId(), idPost);
		Post postTmp = postRepo.findById(idPost).orElseThrow();

		if (likesTmp.isPresent()) {

			
			if (postTmp.getCount() > 0L) {
				postTmp.setCount(postTmp.getCount() - 1L);

				likesRepo.delete(likesTmp.get());
				LikeResponseDto likeResponseDto = new LikeResponseDto(postTmp.getCount(), false);
				return likeResponseDto;
			}

		} 
			LikeResponseDto likeResponseDto = new LikeResponseDto(postTmp.getCount(), false);

			return likeResponseDto;
				
	}
	
	
	public List<LikeFriendResponseDto> getListLike(Long idPost) {
		System.out.println("getListLike");
		List <Likes> listLike=likesRepo.loadLikesFromPost(idPost);

		List <LikeFriendResponseDto> listLikeDto=new ArrayList<LikeFriendResponseDto>();
		
		for(Likes l:listLike) {
			LikeFriendResponseDto dto=new LikeFriendResponseDto(l.getaChiPiace().getId(),l.getaChiPiace().getProfiloUser().getId(),l.getaChiPiace().getUsername(), l.getaChiPiace().getProfiloUser().getFotoProfilo().getUrl());
			listLikeDto.add(dto);
		}
		return listLikeDto;
	}

}
