package com.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "profilo")
	private Profilo profilo;
	
	@Column(name = "testo")
	private String testo;
	
	@OneToMany(mappedBy = "postFoto")
	private List<Foto> foto;
	
	@Column(name = "data")
	private LocalDateTime data;
	
	@Column(name = "count")
	private Long count;

	public Long getCount() {
		return count;
	}


	public void setCount(Long count) {
		this.count = count;
	}


	public Long getId() {
		return id;
	}


	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}


	public List<Foto> getFoto() {
		return foto;
	}




	public void setFoto(List<Foto> foto) {
		this.foto = foto;
	}


	public Profilo getProfilo() {
		return profilo;
	}


	public void setProfilo(Profilo profilo) {
		this.profilo = profilo;
	}


	public LocalDateTime getData() {
		return data;
	}


	public void setData(LocalDateTime data) {
		this.data = data;
	}





	
	
	
}
