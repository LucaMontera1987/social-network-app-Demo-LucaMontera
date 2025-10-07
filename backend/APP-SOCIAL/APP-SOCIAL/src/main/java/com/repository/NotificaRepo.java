package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Notifica;

public interface NotificaRepo extends JpaRepository<Notifica, Long>{
	
	@Query("SELECT n FROM Notifica n WHERE n.visualizzazione ='NON_LETTO' AND n.id_destinatario =:id ORDER BY n.date DESC ")
	List<Notifica> loadNotifica(@Param("id") Long id);

	@Query("SELECT n FROM Notifica n WHERE n.id_evento =:id_evento")
		Optional<Notifica> notificaById_evento(@Param("id_evento") Long id_evento);
	
	
	@Query("SELECT n FROM Notifica n WHERE n.id_evento =:id_evento AND n.id_destinatario =:id_destinatario")
	Optional<Notifica> notificaLetta(@Param("id_evento") Long id_evento, @Param("id_destinatario") Long id_destinatario);
	
	@Query("SELECT n FROM Notifica n WHERE n.visualizzazione ='NON_LETTO' AND n.tipoNotifica ='MESSAGGIO_PRIVATO' "
			+ "AND n.id_destinatario =:id_destinatario")
	List<Notifica> notificaNotReadMessage(@Param("id_destinatario") Long id_destinatario);
	
}
