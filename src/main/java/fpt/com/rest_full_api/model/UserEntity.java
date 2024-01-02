package fpt.com.rest_full_api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fpt.com.rest_full_api.model.emum.Rank;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@Column(name = "first_name", nullable = true)
	private String firstName;

	@Column(name = "last_name", nullable = true)
	private String lastName;

	@Column(name = "password", nullable = true)
	private String password;

	@Column(name = "email")
	private String email;
	@Column(name = "image_url", nullable = true)
	private String imageUrl;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles = new ArrayList<>();
	@Column(name = "mobile", nullable = true)
	private String mobile;

	@Column(name = "points", nullable = true)
	private float points;

	@Column(name = "user_rank", nullable = true)
	@Enumerated(EnumType.STRING)
	private Rank rank;

	@Column(name = "active", nullable = true)
	private boolean active;

	@Enumerated(EnumType.STRING)
	private AuthProvider provider;

	@Column(name = "provider_id", nullable = true)
	private String providerId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();

	@Embedded
	@ElementCollection
	@CollectionTable(name = "payment_information", joinColumns = @JoinColumn(name = "user_id"))
	private List<PaymentInformation> paymentInformation = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Rating> ratings = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviews = new ArrayList<>();
	@Column(name = "activation_code")
	private String activationCode;

	@Column(name = "password_reset_code")
	private String passwordResetCode;

	private LocalDateTime createdAt;

}
