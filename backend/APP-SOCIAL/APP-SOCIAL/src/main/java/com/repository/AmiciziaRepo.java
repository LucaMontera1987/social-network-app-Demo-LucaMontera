package com.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Amicizia;

public interface  AmiciziaRepo extends JpaRepository<Amicizia, Long>{

	
	@Query("SELECT a FROM Amicizia a WHERE a.statoAmicizia ='ACCETTA' AND "
			+ "((a.amiciziaInviata.id =:idA AND a.amiciziaRicevuta.id =:idB )"
			+ "OR"
			+ "(a.amiciziaInviata.id =:idB AND a.amiciziaRicevuta.id =:idA))")
	
	List<Amicizia> loadAmicizia(@Param("idA") Long idA, @Param("idB") Long idB);
	
	
	@Query ("SELECT a FROM Amicizia a WHERE a.statoAmicizia ='ACCETTA' AND ( a.amiciziaInviata.id =:idA OR a.amiciziaRicevuta.id =:idA)")
List<Amicizia> listAmiciziaAccettata(@Param ("idA") Long idA);
	
	
	
	@Query("SELECT a FROM Amicizia a WHERE "
			+ "((a.amiciziaInviata.id =:idA AND a.amiciziaRicevuta.id =:idB )"
			+ "OR"
			+ "(a.amiciziaInviata.id =:idB AND a.amiciziaRicevuta.id =:idA))")
	
	Optional<Amicizia> controllIfAmiciziaIsTrue(@Param("idA") Long idA, @Param("idB") Long idB);
	
	
	
	
}

