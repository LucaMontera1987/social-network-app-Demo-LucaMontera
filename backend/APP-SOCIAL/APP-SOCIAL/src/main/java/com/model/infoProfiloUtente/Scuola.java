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
public class Scuola {
	
	
		  @Id 
		  @GeneratedValue(strategy = GenerationType.IDENTITY)
		  private Long id;
		  
		  @ManyToOne
		  @JoinColumn(name = "scuolaProfilo")
		  private Profilo scuolaProfilo;
		  
		  private String schoolName;   
		  // es. "Liceo X" o "Uni Y"
		  private String degree;       
		  // es. "Laurea Triennale"
		  private LocalDate startDate;
		  
		  private LocalDate endDate;

		public Long getId() {
			return id;
		}

	
		public String getSchoolName() {
			return schoolName;
		}

		public void setSchoolName(String schoolName) {
			this.schoolName = schoolName;
		}

		public String getDegree() {
			return degree;
		}

		public void setDegree(String degree) {
			this.degree = degree;
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


		public Profilo getScuolaProfilo() {
			return scuolaProfilo;
		}


		public void setScuolaProfilo(Profilo scuolaProfilo) {
			this.scuolaProfilo = scuolaProfilo;
		}


		
	
		  
		  
}
