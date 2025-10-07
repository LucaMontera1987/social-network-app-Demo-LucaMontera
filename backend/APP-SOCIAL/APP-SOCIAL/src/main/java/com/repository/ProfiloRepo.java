package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dto.profilo.ProfiloResponseDto;
import com.model.Profilo;

public interface ProfiloRepo extends JpaRepository<Profilo, Long>{

	@Query("SELECT p FROM Profilo p WHERE p.id =:id ")
	Optional<Profilo> profiloUser (@Param ("id") Long idProfilo);
	
	@Query("""
		SELECT p
			
			   FROM Profilo p
			   
			   LEFT JOIN FETCH p.FotoProfilo fp
			   LEFT JOIN FETCH p.FotoCopertina fc
			   LEFT JOIN FETCH p.listaFotoProfilo fl
			 WHERE
			 p.id =:id""")
			 
			 
	Optional<ProfiloResponseDto> getProfile(@Param("id") Long id);
	

	
	
	
}