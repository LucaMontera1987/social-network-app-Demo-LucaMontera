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
	public class Likes implements Serializable{

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@ManyToOne
		@JoinColumn(name = "id_post")
		private Post likePost;
		
		@ManyToOne
		@JoinColumn(name = "a_chi_piace")
		private User aChiPiace;
		
	


		public Long getId() {
			return id;
		}


		public Post getLikePost() {
			return likePost;
		}


		public void setLikePost(Post likePost) {
			this.likePost = likePost;
		}

		public User getaChiPiace() {
			return aChiPiace;
		}


		public void setaChiPiace(User aChiPiace) {
			this.aChiPiace = aChiPiace;
		}
		
		
		
		

}
