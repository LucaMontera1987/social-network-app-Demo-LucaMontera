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
public class Commento implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_post")
	private Post commentiPost;
	
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User commentoUser;
	
	@Column(name = "contenuto")
	private String contenuto;
	
	
	
	@Column(name = "data")
	private LocalDateTime date=LocalDateTime.now();

	public Long getId() {
		return id;
	}

	
	public Post getCommentiPost() {
		return commentiPost;
	}

	public void setCommentiPost(Post commentiPost) {
		this.commentiPost = commentiPost;
	}

	public User getCommentoUser() {
		return commentoUser;
	}

	public void setCommentoUser(User commentoUser) {
		this.commentoUser = commentoUser;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}


	public LocalDateTime getDate() {
		return date;
	}


	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	
	
	
}
