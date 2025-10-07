package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Likes;

public interface Likesrepo extends JpaRepository<Likes, Long>{
	
	@Query("SELECT COUNT (l) FROM Likes l WHERE l.likePost.id =:id")
	Long countLikes(@Param("id") Long id);
	
	@Query("SELECT l FROM Likes l LEFT JOIN FETCH l.aChiPiace WHERE l.likePost.id =:id")
	List<Likes> loadLikesFromPost(@Param("id") Long id);
	
	@Query("SELECT l FROM Likes l WHERE l.aChiPiace.id =:idUser AND l.likePost.id =:idPost")
	Optional<Likes> controlIfLikeIsPresent(@Param("idUser") Long idUser, @Param("idPost") Long idPost);
	

}
	