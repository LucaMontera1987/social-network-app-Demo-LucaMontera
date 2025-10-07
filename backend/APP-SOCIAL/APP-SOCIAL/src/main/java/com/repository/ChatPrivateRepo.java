package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.ChatPrivate;

public interface ChatPrivateRepo extends JpaRepository<ChatPrivate, Long> {
	
	@Query("SELECT c FROM ChatPrivate c WHERE"
			+ " (c.utenteInvia.id =:utenteA AND c.utenteRiceve.id =:utenteB) "
			+ "OR "
			+ "(c.utenteInvia.id =:utenteB AND c.utenteRiceve.id =:utenteA) ORDER BY c.dateChat ASC")
	List<ChatPrivate> loadChat(@Param("utenteA") Long idInvia, @Param ("utenteB") Long idRiceve);

}
