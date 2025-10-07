package com.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ChatPrivate implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_utente_invia")
	private User utenteInvia;
	
	@ManyToOne
	@JoinColumn(name = "id_utente_riceve")
	private User utenteRiceve;
	
	@Column(name = "contenuto", nullable = false)
	private String contenuto;
	
	@Column
	private LocalDateTime dateChat;

	public Long getId() {
		return id;
	}

	
	public User getUtenteInvia() {
		return utenteInvia;
	}

	public void setUtenteInvia(User utenteInvia) {
		this.utenteInvia = utenteInvia;
	}

	public User getUtenteRiceve() {
		return utenteRiceve;
	}

	public void setUtenteRiceve(User utenteRiceve) {
		this.utenteRiceve = utenteRiceve;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}


	public LocalDateTime getDateChat() {
		return dateChat;
	}


	public void setDateChat(LocalDateTime dateChat) {
		this.dateChat = dateChat;
	}
	
	
	
}
