package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

	@Query("SELECT p FROM Post p WHERE p.profilo.id =:id")
	List<Post> findPostByProfiloId(@Param("id") Long id);
	
	@Query("SELECT p FROM Post p LEFT JOIN FETCH p.foto WHERE p.id =:id")
	Optional<Post> recevedPostAndUrlPhoto(@Param("id") Long id);
	
	@Query("SELECT p FROM Post p  WHERE p.profilo.id =:id")
	List <Post> recevedAllPostByProfilo(@Param("id") Long id);
	
	@Query("SELECT p FROM Post p WHERE p.profilo.id IN :idProfili ORDER BY p.data DESC")
	Page<Post> findAllByUserIdInOrderByCreatedAtDesc(@Param("idProfili") List<Long> idProfili, Pageable pageable);

}
