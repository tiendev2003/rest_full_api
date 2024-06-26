package fpt.com.rest_full_api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String review;
	private float star;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	@JsonIgnore
	private Product product;

	@ManyToOne
	@JoinColumn(name="user_id")
	private UserEntity user;
	
	private LocalDateTime createdAt;
	
	public Review() {
		
	}

	public Review(Long id, String review, float star, Product product, UserEntity user, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.review = review;
		this.star = star;
		this.product = product;
		this.user = user;
		this.createdAt = createdAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	//setter and getter

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getStar() {
		return star;
	}

	public void setStar(float star) {
		this.star = star;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
