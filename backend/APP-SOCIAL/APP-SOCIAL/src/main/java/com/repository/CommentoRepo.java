package com.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Commento;

public interface CommentoRepo extends JpaRepository<Commento, Long>{
	
	@Query("SELECT c FROM Commento c WHERE c.commentiPost.id =:id ORDER BY c.date ASC")
	Page<Commento> loadCommentoPageable  (@Param ("id") Long id, Pageable pageable);
	
	@Query("SELECT c FROM Commento c WHERE c.commentiPost.id =:id ORDER BY c.date ASC")
	List<Commento> loadCommento  (@Param ("id") Long id);
	
	
	
	@Query("SELECT COUNT(c) FROM Commento c WHERE c.commentiPost.id =:id ")
	Long countCommenti  (@Param ("id") Long id);

}
