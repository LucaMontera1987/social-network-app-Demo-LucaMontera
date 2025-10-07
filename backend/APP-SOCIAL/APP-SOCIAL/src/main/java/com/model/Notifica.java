package com.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.enums.StatoVisualizzazione;
import com.enums.tipoNotifica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Notifica implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_autore")
	private User id_autore;

	@Column(name = "id_destinatario")
	private Long id_destinatario;
	
	@Column(name = "usernameDestinatario")
	private String usernameDestinatario;

	@Enumerated(EnumType.STRING)
	@Column(name = "stato")
	private StatoVisualizzazione visualizzazione;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipoNotifica")
	private tipoNotifica tipoNotifica;

	@Column(name = "contenuto")
	private String contenuto;

	@Column(name = "id_evento")
	private Long id_evento;

	@Column
	private LocalDateTime date;
	
	@Column(name = "postId")
	private Long postId;

	public User getId_autore() {
		return id_autore;
	}

	public void setId_autore(User id_autore) {
		this.id_autore = id_autore;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public StatoVisualizzazione getVisualizzazione() {
		return visualizzazione;
	}

	public void setVisualizzazione(StatoVisualizzazione visualizzazione) {
		this.visualizzazione = visualizzazione;
	}

	public tipoNotifica getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(tipoNotifica tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}

	public User getId_user_notifica() {
		return id_autore;
	}

	public void setId_user_notifica(User id_autore) {
		this.id_autore = id_autore;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public Long getId_destinatario() {
		return id_destinatario;
	}

	public void setId_destinatario(Long id_destinatario) {
		this.id_destinatario = id_destinatario;
	}

	public Long getId_evento() {
		return id_evento;
	}

	public void setId_evento(Long id_evento) {
		this.id_evento = id_evento;
	}

	public String getUsernameDestinatario() {
		return usernameDestinatario;
	}

	public void setUsernameDestinatario(String usernameDestinatario) {
		this.usernameDestinatario = usernameDestinatario;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	
	
}
