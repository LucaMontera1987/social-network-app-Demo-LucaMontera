package com.model.infoProfiloUtente;

import java.time.LocalDate;

import com.model.Profilo;
import com.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Lavoro {
	
	  @Id 
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  
	  private String company;
	  private String role;
	  private LocalDate startDate;
	  private LocalDate endDate; 
	  
	  @ManyToOne
	  @JoinColumn(name = "lavoroProfilo")
	  private Profilo lavoroProfilo;
	  
	public Long getId() {
		return id;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Profilo getLavoroProfilo() {
		return lavoroProfilo;
	}

	public void setLavoroProfilo(Profilo lavoroProfilo) {
		this.lavoroProfilo = lavoroProfilo;
	}

	
	  
	  

}
