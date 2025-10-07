package com.model;

import java.io.Serializable;

import com.enums.StatoAmicizia;

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
public class Amicizia implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "utenteAmiciziaInviata")
	private User amiciziaInviata;
	
	@ManyToOne
	@JoinColumn(name = "utenteAmiciziaRicevuta")
	private User amiciziaRicevuta;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "statoAmicizia")
	private StatoAmicizia statoAmicizia;

	public Long getId() {
		return id;
	}



	public User getAmiciziaInviata() {
		return amiciziaInviata;
	}

	public void setAmiciziaInviata(User amiciziaInviata) {
		this.amiciziaInviata = amiciziaInviata;
	}

	public User getAmiciziaRicevuta() {
		return amiciziaRicevuta;
	}

	public void setAmiciziaRicevuta(User amiciziaRicevuta) {
		this.amiciziaRicevuta = amiciziaRicevuta;
	}

	public StatoAmicizia getStatoAmicizia() {
		return statoAmicizia;
	}

	public void setStatoAmicizia(StatoAmicizia statoAmicizia) {
		this.statoAmicizia = statoAmicizia;
	}
	
	
	
	
}
