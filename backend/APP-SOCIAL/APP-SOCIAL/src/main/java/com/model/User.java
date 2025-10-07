package com.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.enums.Roles;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "cognome", nullable = false)
	private String cognome;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false)
	private String email;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "data_di_nascita", nullable = false)
	private LocalDate dataNascita;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "roles")
	private Roles roles;
	
	
	@OneToOne
	@JoinColumn(name = "id_Profilo")
    private Profilo profiloUser;
	
	
	
	public Profilo getProfiloUser() {
		return profiloUser;
	}


	public void setProfiloUser(Profilo profiloUser) {
		this.profiloUser = profiloUser;
	}


	public Long getId() {
		return id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}


	public Roles getRoles() {
		return roles;
	}


	public void setRoles(Roles roles) {
		this.roles = roles;
	}


	
	
	

//	@OneToMany(mappedBy = "amiciziaInviata")
//	private List<Amicizia> listaAmiciziaInviata;
//
//	@OneToMany(mappedBy = "amiciziaRicevuta")
//	private List<Amicizia> listaAmiciziaRicevuta;

//	@OneToMany(mappedBy = "postUtente")
//	private List<Post> post;
//	
//	@OneToMany(mappedBy = "notifica")
//	private List<Notifica> notifica;
//	
//	@OneToMany(mappedBy = "messaggioChat")
//	private List<ChatPrivate> messaggioChat;
	
	
	
	
	
}
