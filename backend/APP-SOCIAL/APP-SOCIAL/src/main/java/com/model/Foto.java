package com.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Foto implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "url", nullable = false)
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "FotoProfilo")
	private Profilo profilo;
	
	
	@ManyToOne
	@JoinColumn(name = "id_post")
	private Post postFoto;

	public Long getId() {
		return id;
	}

	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Post getPostFoto() {
		return postFoto;
	}

	public void setPostFoto(Post postFoto) {
		this.postFoto = postFoto;
	}



	public Profilo getProfilo() {
		return profilo;
	}



	public void setProfilo(Profilo profilo) {
		this.profilo = profilo;
	}






	


	

	
	
	

}
