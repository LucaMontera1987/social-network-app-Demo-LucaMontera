package com.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.model.infoProfiloUtente.Lavoro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Profilo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "immagine_di_copertina")
	private Foto FotoCopertina;

	@OneToOne
	@JoinColumn(name = "immagine_di_profilo")
	private Foto FotoProfilo;

	@OneToMany(mappedBy = "profilo")
	private Set<Foto> listaFotoProfilo;

	@OneToMany(mappedBy = "lavoroProfilo")
	private List<Lavoro> lavoroProfilo;

	@OneToMany(mappedBy = "profilo")
	private List<Post> postProfilo;

	@OneToOne(mappedBy = "profiloUser")
		private User user;

	public Long getId() {
		return id;
	}

	
	
	
	public void setId(Long id) {
		this.id = id;
	}




	public Set<Foto> getListaFotoProfilo() {
		return listaFotoProfilo;
	}

	public void setListaFotoProfilo(Set<Foto> listaFotoProfilo) {
		this.listaFotoProfilo = listaFotoProfilo;
	}

	public List<Lavoro> getLavoroProfilo() {
		return lavoroProfilo;
	}

	public void setLavoroProfilo(List<Lavoro> lavoroProfilo) {
		this.lavoroProfilo = lavoroProfilo;
	}

	public Foto getFotoCopertina() {
		return FotoCopertina;
	}

	public void setFotoCopertina(Foto fotoCopertina) {
		FotoCopertina = fotoCopertina;
	}

	public Foto getFotoProfilo() {
		return FotoProfilo;
	}

	public void setFotoProfilo(Foto fotoProfilo) {
		FotoProfilo = fotoProfilo;
	}

	public List<Post> getPostProfilo() {
		return postProfilo;
	}

	public void setPostProfilo(List<Post> postProfilo) {
		this.postProfilo = postProfilo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

}
