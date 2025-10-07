package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Foto;

public interface FotoRepo extends JpaRepository<Foto, Long>{
	
	@Query("SELECT f.url FROM Foto f  WHERE f.postFoto.id =:id")
	List<String> loadFotoPost(@Param("id") Long id);

	@Query(" SELECT f FROM Foto f  LEFT JOIN FETCH f.url LEFT JOIN FETCH f.postFoto WHERE f.postFoto.id =:id ")

          
	List<String> loadFotoByIdPost(@Param("id") Long id);

	
	
}
